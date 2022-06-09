package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;

public class DeleteProcController implements Command {
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
	
		int num = Integer.parseInt(req.getParameter("num"));
		String pass = req.getParameter("pass");
		System.out.println("num :" + num);
		System.out.println("pass : " + pass);
		
		BoardDao dao =  BoardDao.getInstance();
		int check = dao.deleteArticle(num, pass);
		
		req.setAttribute("num", num);
		req.setAttribute("check", check);
		
		return "deleteproc";
	}

}
