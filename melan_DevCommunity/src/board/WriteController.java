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

@SuppressWarnings("serial")
@WebServlet("/board/write.do")
public class WriteController extends HttpServlet {
	
	private final String prefix = "../WEB-INF";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("userId") != null) { // 로그인 상태라면 회원아이디와 고유번호를 저장하고
			String memId = req.getSession().getAttribute("userId").toString();
			String memNum = req.getSession().getAttribute("userNum").toString();	
		} else { // 로그인 상태가 아니라면
			JSFunction.alertLocation(resp, "로그인 후 이용해주십시오.", "../member/login.do");
			return;
		}
		req.getRequestDispatcher(prefix + "/board/Write.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 업로드 관련 처리
		String saveDirectory = req.getServletContext().getRealPath("/WEB-INF/Uploads"); // 저장할 디렉터리
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize")); // 파일 최대 크기(5MB)
		String encoding = "UTF-8"; // 인코딩 방식

		try {
			MultipartRequest mr = new MultipartRequest(req, saveDirectory, maxPostSize, encoding);
			
			// 다른 폼값 먼저 받기
			String memNum = req.getSession().getAttribute("userNum").toString();
			String memId = req.getSession().getAttribute("userId").toString();
			String title = mr.getParameter("title");
			String content = mr.getParameter("content");
			
			// DTO 생성하고 값 채우기
			BoardDTO dto = new BoardDTO();
			dto.setMemNum(memNum);
			dto.setMemId(memId);
			dto.setTitle(title);
			dto.setContent(content);
			
			if (mr.getFilesystemName("attachedFile") != null) { // 첨부파일 있다면
				// 새로운 파일명 생성
				String fileName = mr.getFilesystemName("attachedFile"); // 현재 파일 이름
				System.out.println("fileName : " + fileName);
		
				String ext = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자(.txt)
				System.out.println("ext : " + ext);
				String now = new SimpleDateFormat("yyMMdd_HmsS").format(new Date());
				System.out.println("now : " + now);
		
				// 새로운 파일 이름("작성자_업로드일시.확장자")
				String newFileName = req.getSession().getAttribute("userId").toString() + "_" + now + ext; 
				System.out.println("newFileName : " + newFileName);

				// 파일명 변경
				File oldFile = new File(saveDirectory + File.separator + fileName);
				File newFile = new File(saveDirectory + File.separator + newFileName);
				oldFile.renameTo(newFile);
				
				// 파일 관련 값 채우기
				dto.setOfile(fileName);
				dto.setSfile(newFileName);
			}
				
			// DAO를 통해 DB에 반영
			BoardDAO dao = new BoardDAO();
			int iResult = dao.insertBoard(dto);
			dao.close();
			
			if (iResult != 1) { // 게시글 등록 실패 시 
				JSFunction.alertBack(resp, "파일 업로드에 실패했습니다!ㅠㅠ");
			} else { // 게시글 등록 성공 시 게시판 목록으로 리다이렉션
				JSFunction.alertLocation(resp, "성공적으로 글이 등록되었습니다!", "./list.do");
			}
			
		} catch (IOException e) {
			JSFunction.alertBack(resp, "파일이 제한용량을 초과합니다!ㅠㅠ");
			e.printStackTrace();
		} catch (Exception e) {
			JSFunction.alertBack(resp, "파일 업로드에 실패했습니다!ㅠㅠ");
			e.printStackTrace();
		}
	}
}
