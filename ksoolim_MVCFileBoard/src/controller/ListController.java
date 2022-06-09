package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;
import mvcfileboard.BoardDto;

public class ListController implements Command {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		BoardDao dao = BoardDao.getInstance(); 
		List<BoardDto> articleList = dao.getArticles();
		int count = dao.getArticleCount();
		
		if( count > 0) {
		req.setAttribute("articleList", articleList);
		req.setAttribute("count", count);
		} else {
			req.setAttribute("count", 0);
		}
		return "listview";
	
	}
	}
