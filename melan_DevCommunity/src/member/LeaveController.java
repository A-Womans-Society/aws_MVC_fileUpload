package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet("/member/leave.do")
public class LeaveController extends HttpServlet{
	
	private final String prefix = "../WEB-INF";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(prefix + "/member/Leave.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String memId = session.getAttribute("userId").toString();
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMemberDTO(memId);
		BoardDAO bdao = new BoardDAO();
		bdao.deletePostByMemId(memId); // 해당 회원이 작성한 게시물 모두 삭제
		mdao.deleteMember(mdto.getMemNum()); // 해당 회원 정보 삭제
		session.invalidate(); // 세션 무효화
		
		mdao.close();
		bdao.close();
		
		JSFunction.alertLocation(resp, "탈퇴처리가 완료됐습니다! 다음에 또 봐요~", "../board/list.do");
	}
}
