package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/member/login.do")
public class LoginController extends HttpServlet{
	
	private final String prefix = "../WEB-INF";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("userId") == null) { // 로그인 상태가 아니라면
			req.getRequestDispatcher(prefix + "/member/Login.jsp").forward(req, resp);
			return;
		} else { // 로그인 상태라면
			resp.sendRedirect("./mypage.do");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼으로부터 받은 아이디와 패스워드
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		
		// 회원 테이블 DAO를 통해 회원 정보 DTO 획득
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMemberDTO(userId, userPwd);
		mdao.close();
		
		// 로그인 성공 여부에 따른 처리
		HttpSession session = req.getSession();
		if (mdto.getMemId() != null) {
			// 로그인 성공  -> session 영역에 아이디와 고유번호 저장 후, 마이페이지로 이동
			session.setAttribute("userId", mdto.getMemId());
			session.setAttribute("userNum", mdto.getMemNum());
			resp.sendRedirect("./mypage.do");
		} else {
			// 로그인 실패 -> request 영역에 오류 메시지 저장 후, 로그인 페이지로 포워드
			req.setAttribute("LoginErrMsg", "아이디나 비밀번호가 틀립니다 🤔");
			req.getRequestDispatcher(prefix + "/member/Login.jsp").forward(req, resp);
		}
	}
}
