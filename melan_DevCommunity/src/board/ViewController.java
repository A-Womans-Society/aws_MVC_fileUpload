package board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 불러오기
		BoardDAO dao = new BoardDAO();
		String boardNum  = req.getParameter("boardNum");
		dao.updateVisitCount(boardNum);
		BoardDTO dto = dao.selectView(boardNum);
		dao.close();
		
		// 줄바꿈 처리
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		// 게시물(dto) 저장 후 뷰로 포워드
		req.setAttribute("dto", dto);	
		req.getRequestDispatcher("./View.jsp").forward(req, resp);

		
	}
}
