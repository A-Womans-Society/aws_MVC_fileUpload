package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;
import mvcfileboard.BoardDto;

public class GetUpdateController implements Command{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		
		BoardDao dao = BoardDao.getInstance();
				
		int num = Integer.parseInt(req.getParameter("num"));
		BoardDto article = dao.updateGetArticle(num);
		
		req.setAttribute("article", article);
	
		return "updateform";
	}

}
