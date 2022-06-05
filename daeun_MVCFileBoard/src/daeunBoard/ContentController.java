package daeunBoard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/content")
public class ContentController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num = Integer.parseInt(req.getParameter("num"));
		int index = Integer.parseInt(req.getParameter("index"));
		BoardDao dbPro = BoardDao.getInstance();
		BoardVo article = dbPro.listOne(num);
		req.setAttribute("article", article);
		req.setAttribute("index", index);
		req.getRequestDispatcher("/WEB-INF/views/content.jsp").forward(req, resp);
	}
}
