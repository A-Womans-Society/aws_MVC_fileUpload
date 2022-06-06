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
@WebServlet("/member/join.do")
public class JoinController extends HttpServlet{
	
	private final String prefix = "../WEB-INF";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(prefix + "/member/Join.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 폼값 받기
		String memId = req.getParameter("memId");
		String memPwd = req.getParameter("memPwd");
		
		// 비밀번호 검증
		try {
			int memPwdToInt = Integer.parseInt(memPwd);
			System.out.println("비밀번호를 정수로 바꾼 값 : " + memPwdToInt);
		} catch (NumberFormatException e) {
			JSFunction.alertBack(resp, "비밀번호는 숫자 4자리를 입력하세요!");
			return;
		}
		
		// DTO 생성해서 폼값 세팅
		MemberDTO mdto = new MemberDTO();
		
		mdto.setMemId(memId);
		mdto.setMemPwd(memPwd);
		
		// DAO 생성해서 회원등록
		MemberDAO mdao = new MemberDAO();
		int iResult = mdao.insertMember(mdto);
		mdao.close();
		
		HttpSession session = req.getSession();
		if (iResult == 1) { // 회원등록 성공 시
			session.setAttribute("memId", memId);
			JSFunction.alertLocation(resp, "가입이 완료되었습니다!", "./login.do");
		} else { // 회원등록 실패 시
			JSFunction.alertBack(resp, "회원가입에 실패하였습니다. 이미 계정이 있으신가요?");
		}
	}
}
