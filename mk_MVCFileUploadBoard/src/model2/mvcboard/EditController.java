package model2.mvcboard;

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
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/mvcboard/edit.do")
public class EditController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idx = req.getParameter("idx"); // 수정할 게시물의 일련번호를 받아
		MVCBoardDAO dao = new MVCBoardDAO(); // dao객체 생성하고
		MVCBoardDTO dto = dao.selectView(idx); // 기존 게시물의 내용을 담은 dto 객체를 얻어
		req.setAttribute("dto", dto); // request 영역에 저장한 다음
		req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp); // Edit.jsp로 포워드 함.

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 파일 업로드 처리
		// 업로드 디렉터리의 물리적 경로 확인
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");

		// 초기화 매개변수로 설정한 첨부파일 최대 용량 확인
		ServletContext application = this.getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));

		// 파일 업로드 - uploadFile 메서드 호출
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);

		if (mr == null) {
			// 파일 업로드 실패 - 경고창으로 작성 페이지로 다시 이동하게 됨.
			JSFunction.alertBack(resp, "첨부파일이 제한 용량을 초과합니다.");
			return;
		}
 
		// 파일 업로드 외 처리
		// 수정 내용을 매개변수에서 얻어옴
        String idx = mr.getParameter("idx");
        String prevOfile = mr.getParameter("prevOfile");
        String prevSfile = mr.getParameter("prevSfile");

        String name = mr.getParameter("name");
        String title = mr.getParameter("title");
        String content = mr.getParameter("content");
		
		// 비밀번호는 session에서 가져옴 - passcontroller 서블릿에서 저장한 갑 가져옴.
        HttpSession session = req.getSession();
        String pass = (String)session.getAttribute("pass");

		// dto에 저장
        MVCBoardDTO dto = new MVCBoardDTO();
        dto.setIdx(idx);
        dto.setName(name);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPass(pass);

		// 원본 파일명과 저장된 파일 이름 설정
		String fileName = mr.getFilesystemName("ofile");
		if (fileName != null) {
			// 첨부 파일이 있을 경우 파일명 변경
			// 새로운 파일명 생성
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = now + ext;
			// 파일명 변경 - 원래 파일명과 저장된파일명을 따로 기록
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);
			//dto에 저장
			dto.setOfile(fileName); // 원래 파일 이름
			dto.setSfile(newFileName); // 서번에 저장된 파일 이름

			// 기존 파일 삭제
			FileUtil.deleteFile(req, "/Uploads", prevSfile);
		}
		else {
			// 첨부파일이 없으면 기존 이름 유지
			dto.setOfile(prevOfile);
			dto.setSfile(prevSfile);
		}

		// db에 수정 내용 반영 - updatepost 메서드 호출로 게시물 수정
		 MVCBoardDAO dao = new MVCBoardDAO();
	     int result = dao.updatePost(dto);
	     dao.close();

		System.out.println(result);
		// 성공 or 실패? 수정이 정상적으로 처리가 된다면 session 영역에 저장된 비밀번호는 삭제하고
		// 상세보기 뷰로 이동해 수정된 내용을 확인시켜줌
        if (result == 1) {  // 수정 성공
            session.removeAttribute("pass");
            resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
        }
        else {
            JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.",
                "../mvcboard/view.do?idx=" + idx);
        }
    }
}
