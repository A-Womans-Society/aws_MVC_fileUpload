package controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcfileboard.BoardDao;
import mvcfileboard.BoardDto;

public class WriteController implements Command {
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		BoardDao dao = BoardDao.getInstance();
		BoardDto dto = new BoardDto();
		req.setCharacterEncoding("utf-8");
		int readcount = 0;
		int num = dto.getNum();
		
	try {
		dto.setNum(num);
		dto.setWriter(req.getParameter("writer"));
		dto.setSubject(req.getParameter("subject"));
		dto.setPass(req.getParameter("pass"));
		dto.setReadcount(readcount);
		dto.setRegdate(new Timestamp(System.currentTimeMillis()));
		dto.setContent(req.getParameter("content"));
		dto.setFilename(req.getParameter("uploadfile"));
		
		
	} catch(NumberFormatException e) {	
		
	} catch (Exception e) {
		
	}

	dao.insertArticle(dto);
		
		
		return "writesuccess";
	
	}
	
	}