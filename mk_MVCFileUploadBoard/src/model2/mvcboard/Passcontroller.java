package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mvcboard/pass.do")
public class Passcontroller extends HttpServlet{
		
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 req.setAttribute("mode", req.getParameter("mode"));
		 req.getRequestDispatcher("/14MVCBoard/Pass.jsp").forward(req, resp);
	}
}
