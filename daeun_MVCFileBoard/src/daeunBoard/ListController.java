package daeunBoard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/list")
public class ListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int count = 0;
		List<BoardVo> article_list = null;
		BoardDao dbPro = BoardDao.getInstance();
		count = dbPro.counter();
		if (count > 0) {
			article_list = dbPro.listAll();
			req.setAttribute("article_list", article_list);
		}
		req.setAttribute("count", count);
		req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
	}
}
