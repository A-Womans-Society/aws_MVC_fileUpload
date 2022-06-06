package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet(value = {"/member/update.do"}, initParams = {
		@WebInitParam(name = "mode", value = "id"),
		@WebInitParam(name = "mode", value = "pwd")
})
public class MemInfoController extends HttpServlet{
	
	private final String prefix = "../WEB-INF";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("mode").equals("id")) { // 아이디 변경 시
			req.getRequestDispatcher(prefix + "/member/UpdateId.jsp").forward(req, resp);
			return;
		} else if (req.getParameter("mode").equals("pwd")) { // 비밀번호 변경 시
			req.getRequestDispatcher(prefix + "/member/UpdatePwd.jsp").forward(req, resp);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		MemberDAO mdao = new MemberDAO();
		BoardDAO bdao = new BoardDAO();
		
		if (req.getParameter("mode").equals("id")) { // 아이디 변경 시
			//System.out.println(req.getParameter("newId"));
			//System.out.println(session.getAttribute("userId").toString());
			if (req.getParameter("newId").equals(session.getAttribute("userId").toString())) {
				JSFunction.alertBack(resp, "기존 아이디와 신규 아이디가 일치합니다!");
				return;
			} else {
				String memId = session.getAttribute("userId").toString();
				String newId = req.getParameter("newId");
				MemberDTO oldMdto = mdao.getMemberDTO(memId);
				oldMdto.setMemId(newId);
				if (mdao.updateMemId(oldMdto) == 1) { // 아이디 업데이트 
					bdao.updatePostByMemId(newId, memId); // 게시글의 작성자 아이디도 업데이트
					session.setAttribute("userId", newId); // 세션 아이디 업데이트
					bdao.close();
					JSFunction.alertLocation(resp, "아이디가 변경되었습니다!", "./mypage.do");
				} else {
					JSFunction.alertBack(resp, "아이디 변경에 실패했습니다!");
				}
			}			
			return;
		} else if (req.getParameter("mode").equals("pwd")) { // 비밀번호 변경 시
			String memId = session.getAttribute("userId").toString();
			String newPwd = req.getParameter("newPwd");
			MemberDTO oldDto = mdao.getMemberDTO(memId);
			
			if (req.getParameter("oldPwd").equals(oldDto.getMemPwd())) { // 기존 비밀번호가 일치한다면 비밀번호 변경 가능
				oldDto.setMemPwd(newPwd);
				if (mdao.updateMemPwd(oldDto) != 0) { // 비밀번호 업데이트 
					mdao.close();
					JSFunction.alertLocation(resp, "비밀번호가 변경되었습니다!", "./mypage.do");
				} else {
					JSFunction.alertBack(resp, "비밀번호 변경에 실패했습니다!");
				}
			} else {
				JSFunction.alertBack(resp, "기존 비밀번호가 틀립니다!");
			}
		}
	}
}
