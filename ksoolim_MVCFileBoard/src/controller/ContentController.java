package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;
import mvcfileboard.BoardDto;

public class ContentController implements Command{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		
		BoardDao dao = BoardDao.getInstance();
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		BoardDto article = dao.getArticle(num);
		
		req.setAttribute("num", num);
		req.setAttribute("article", article);
		
		return "contentview";
	}

}
