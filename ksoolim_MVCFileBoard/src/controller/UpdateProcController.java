package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;
import mvcfileboard.BoardDto;

public class UpdateProcController implements Command {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		BoardDao dao = BoardDao.getInstance();
		BoardDto dto = new BoardDto();
		
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setWriter(req.getParameter("writer"));
		dto.setSubject(req.getParameter("subject"));
		dto.setPass(req.getParameter("pass"));
		dto.setContent(req.getParameter("content"));
		
		int update = dao.updateArticle(dto);
		
		req.setAttribute("update", update);
		
		return "index";
	}
}
