package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/member/idCheck.do")
@WebServlet(value = {"/member/idCheck.do"}, initParams = {
		@WebInitParam(name = "mode", value = "update"),
		@WebInitParam(name = "memId", value = "")
})
public class IdCheckController extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int result = 0;
		String targetMemId = "";
		MemberDAO mdao = new MemberDAO();
		
		if ("update".equals(req.getParameter("mode"))) {
			targetMemId = req.getParameter("newId");
		} else {
			targetMemId = req.getParameter("memId");
		}
		if (targetMemId.equals("")) {
			result = 2;
		} else {
			System.out.println("(IdCheckController 부름) targetMemId : " + targetMemId);
			
			result = mdao.memIdCheck(targetMemId);
			mdao.close();
		}
		resp.getWriter().print(result);
	}
}
