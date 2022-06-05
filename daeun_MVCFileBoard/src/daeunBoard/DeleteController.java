package daeunBoard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/delete")
public class DeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDao dbPro = BoardDao.getInstance();
		String[] nums = req.getParameter("num").split(",");
		for (String n : nums) {
			dbPro.delete(Integer.parseInt(n));
		}
		resp.sendRedirect(req.getContextPath() + "/board/list");
	}
}
