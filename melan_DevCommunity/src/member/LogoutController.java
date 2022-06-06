package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet("/member/logout.do")
public class LogoutController extends HttpServlet{
	
	//private final String prefix = "../WEB-INF";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//session.removeAttribute("UserId");
		//session.removeAttribute("UserName");

		session.invalidate();
		JSFunction.alertLocation(resp, "로그아웃되었습니다!", "./login.do");	
	}
}
