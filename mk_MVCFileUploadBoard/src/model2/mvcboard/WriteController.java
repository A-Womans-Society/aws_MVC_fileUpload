package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

public class WriteController extends HttpServlet {
	// 진입하기만 위한 메서드는 get, 폼값을 받아 db처리를 하는 메서드는 post
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 작성폼 진입을 위해 doGet메서드 사용, 단순히 글쓰기 페이지로 포워드(Write.jsp)
		req.getRequestDispatcher("/14MVCBoard/Write.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 파일 업로드 처리
		// 업로드 디렉터리의 물리적 경로 확인
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");

		// 초기화 매개변수로 설정한 첨부파일 최대 용량 확인
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		// 파일 업로드  - uploadFile 메서드 호출 
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
		if(mr ==null) {
		//파일 업로드 실패 - 경고창으로 작성 페이지로 다시 이동하게 됨.
		JSFunction.alertLocation(resp, "첨부파일이 제한 용량을 초과합니다.", "../mvcboard/write.do");
		return;
	}
		//2. 파일 업로드 외 처리 - 글쓰기 처리와 똑같음
		//폼값을 DTO에 저장
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName(mr.getParameter("name"));
		dto.setTitle(mr.getParameter("title"));
		dto.setContent(mr.getParameter("content"));
		dto.setPass(mr.getParameter("pass"));
		
		// 원본 파일명과 저장된 파일 이름 설정
		String fileName = mr.getFilesystemName("ofile");
		if(fileName != null) {
			//첨부 파일이 있을 경우 파일명 변경
			//새로운 파일명 생성
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now + ext;
			
			//파일명 변경 - 원래 파일명과 저장된파일명을 따로 기록
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);
			
			dto.setOfile(fileName); //원래 파일 이름
			dto.setSfile(newFileName); //서번에 저장된 파일 이름
		}
		
		//dao를 통해 DB에 게시 내용 저장- 데이터베이스에 기록하기 위해
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.insertWrite(dto);
		dao.close();
		
		//  성공 또는 실패 - 모든 작업이 오류 없이 완료되었다면 목록으로 이동하고, 아니면 페이지로 다시 돌아감.
		if(result == 1) {// 글쓰기 성공
			resp.sendRedirect("../mvcboard/list.do");
		}else { //글쓰기 실패
			resp.sendRedirect("../mvcboard/write.do");
		}
	}
}
