package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JSFunction;

@WebServlet("/member/mypage.do")
public class MyPageController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") == null) {
			JSFunction.alertLocation(resp, "로그인하신 후 마이페이지를 이용하실 수 있습니다!", "./login.do");
			return;
		}
		String memId = session.getAttribute("userId").toString();
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMemberDTO(memId);
		req.setAttribute("mdto", mdto);
		mdao.close();
		req.getRequestDispatcher("MyPage.jsp").forward(req, resp);
	}
}
