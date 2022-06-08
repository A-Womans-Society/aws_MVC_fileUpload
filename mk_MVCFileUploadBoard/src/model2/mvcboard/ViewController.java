package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mvcboard/view.do")
public class ViewController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 게시물 불러오기
		MVCBoardDAO dao = new MVCBoardDAO(); // 게시물 조회 요청이 들어오면 서블릿은 dao 객체를 생성한 후
		String idx = req.getParameter("idx");// 게시물의 일련번호를 매개변수로 받아
		dao.updateVisitCount(idx);// 조회수를 먼저 증가시킨 다음
		MVCBoardDTO dto = dao.selectView(idx); // 게시물 내용을 가져오게 됨.
		dao.close();
		
		// 줄바끔 처리 - HTML문서는 일반 텍스트 문서의 줄바끔을 무시하기 때문에 br태그로 바꿔줌
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		//게시물(dto)저장 후 뷰로 포워드
		 req.setAttribute("dto", dto);
	        req.getRequestDispatcher("../14MVCBoard/View.jsp").forward(req, resp);
	
	}

}
