package board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import utils.JSFunction;

@WebServlet("/board/edit.do")
public class EditController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardNum = req.getParameter("boardNum");
		BoardDAO dao = new BoardDAO();
		BoardDTO bdto = dao.selectView(boardNum);
		req.setAttribute("bdto", bdto);
		
		if (!bdto.getMemId().equals(req.getSession().getAttribute("userId"))) { // 작성자 본인이 아니라면
			JSFunction.alertBack(resp, "작성자 본인만 삭제 가능합니다!");
			return;
		} else { // 작성자 본인이라면
			req.getRequestDispatcher("Edit.jsp").forward(req, resp);			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 파일 업로드 관련 처리
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");

		ServletContext application = getServletContext();
		int maxPostsize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		String encoding = "UTF-8"; 

		// mr 객체 생성(파일 업로드)
		MultipartRequest mr = new MultipartRequest(req, saveDirectory, maxPostsize, encoding);
		
		// 나머지 수정 내용을 매개변수에서 얻어오기
		String boardNum = mr.getParameter("boardNum");
		String memId = req.getSession().getAttribute("userId").toString();
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String prevOfile = mr.getParameter("prevOfile");
		System.out.println("prevOfile : " + prevOfile);
		String prevSfile = mr.getParameter("prevSfile");
		System.out.println("prevSfile : " + prevSfile);

		
		// 게시물 고유번호, 회원아이디 제외하고 나머지 값들 DTO에 저장
		BoardDAO bdao = new BoardDAO();
		BoardDTO bdto = bdao.selectView(boardNum);
		bdto.setTitle(title);
		bdto.setContent(content);
		
		// 원본 파일명과 저장된 파일 이름 설정
		String fileName = mr.getFilesystemName("ofile");
		System.out.println(" 원본 파일명과 저장된 파일 이름 설정 fileName : " + fileName);

		if (fileName != null) {
			// 첨부 파일이 있을 경우 파일명 변경
			// 새로운 파일명 생성
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = req.getSession().getAttribute("userId").toString() + "_" + now + ext;
			System.out.println("새로운 파일명 생성 newFileName : " + newFileName);
			// 파일명 변경
			File oldFile = new File(saveDirectory + File.separator + fileName);
			System.out.println(" 파일명 변경 oldFile : " + oldFile);

			File newFile = new File(saveDirectory + File.separator + newFileName);
			System.out.println("파일명 변경 newFile : " + newFile);

			oldFile.renameTo(newFile);
			
			// DTO에 저장
			bdto.setOfile(fileName); // 원래 파일 이름
			bdto.setSfile(newFileName); // 서버에 저장된 파일 이름
			
			// 기존 파일 삭제
			File file = new File(saveDirectory + File.separator + prevSfile);
			System.out.println(" 기존 파일 삭제 file : " + file);

			if (file.exists()) {
				file.delete();
				System.out.println("기존 파일 삭제 완료");
			} else {
				System.out.println("기존파일 없음");
			}
		} else {
			// 첨부파일이 없으면 기존 이름 유지
			bdto.setOfile(prevOfile);
			bdto.setSfile(prevSfile);
		}
		
		// DB에 수정내용 반영
		int result = bdao.updatePost(bdto);
		bdao.close();
		
		// 성공 or 실패?
		if (result == 1) { // 수정 성공
			JSFunction.alertLocation(resp, "수정이 완료되었습니다!", "./view.do?boardNum=" + boardNum);
		} else { // 수정 실패
			JSFunction.alertBack(resp, "수정에 실패했습니다!");
		}	
	}
}
