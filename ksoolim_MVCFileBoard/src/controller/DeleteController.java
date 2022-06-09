package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteController implements Command{
@Override
public String process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
	
	
	int num = Integer.parseInt(req.getParameter("num"));
	
	
	req.setAttribute("num", new Integer(num));

	
	return "deleteform";
}
}
