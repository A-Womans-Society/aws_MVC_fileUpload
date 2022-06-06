package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDAO;
import member.MemberDTO;
import utils.JSFunction;

@WebServlet(value = {"/reply/edit.do"}, initParams = {
		@WebInitParam(name = "repNum", value = ""),
})
public class ReplyEditController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 해당 댓글 rdto 파라미터에 담기
		String repNum = req.getParameter("repNum");
		ReplyDAO rdao = new ReplyDAO();
		ReplyDTO rdto = rdao.getRdto(repNum);
		req.setAttribute("rdto", rdto);
		rdao.close();
		
		// 댓글 작성자 mdto 파라미터에 담기
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMdtoByNum(rdto.getMemNum());
		req.setAttribute("mdto", mdto);
		mdao.close();
		
		if (!rdto.getMemNum().equals(req.getSession().getAttribute("userNum"))) { // 작성자 본인이 아니라면
			JSFunction.alertBack(resp, "작성자 본인만 삭제 가능합니다!");
			return;
		} else { // 작성자 본인이라면
			req.getRequestDispatcher("EditReply.jsp").forward(req, resp);			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		
		//String memNum = session.getAttribute("userNum").toString();
		String repNum = req.getParameter("repNum");
		String content = req.getParameter("content");
		
		// DTO 생성하고 값 채우기
		ReplyDAO rdao = new ReplyDAO();
		ReplyDTO rdto = rdao.getRdto(repNum);
		rdto.setContent(content);
		String boardNum = rdto.getBoardNum();
		
		// DTO DB에 반영
		int result = rdao.updateReply(rdto);
		rdao.close();
		
		if (result != 1) { // 댓글 등록 실패 시 
			JSFunction.alertBack(resp, "댓글 수정에 실패했습니다!ㅠㅠ");
		} else { // 댓글 수정 성공 시 게시글 상세보기 페이지로 리다이렉션
			JSFunction.alertLocation(resp, "댓글 수정에 성공했습니다!>_<", req.getContextPath() + "/board/view.do?boardNum=" + boardNum);
		}
	}
}
