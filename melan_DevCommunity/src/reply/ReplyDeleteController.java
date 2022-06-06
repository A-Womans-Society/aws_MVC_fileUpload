package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet(value = {"/reply/delete.do"}, initParams = {
		@WebInitParam(name = "repNum", value = ""),
})
public class ReplyDeleteController extends HttpServlet{
	
	//private final String prefix = "../WEB-INF";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if (session.getAttribute("userNum") == null) {
			JSFunction.alertLocation(resp, "먼저 로그인해주세요!", req.getContextPath() + "/member/login.do");
			return;
		}
		String memNum = session.getAttribute("userNum").toString();
		String repNum = req.getParameter("repNum");
		
		ReplyDAO rdao = new ReplyDAO();
		String boardNum = rdao.getRdto(repNum).getBoardNum();
		ReplyLikesDAO ldao = new ReplyLikesDAO();
		if (!rdao.getMemNum(repNum).equals(memNum)) { // 작성자 본인이 아니면
			rdao.close();
			JSFunction.alertBack(resp, "댓글 작성자 본인만 삭제 가능합니다!");
			return;
		} else { // 작성자 본인이라면
			ldao.deleteReplyLikes(repNum);
			int result = rdao.deleteReply(repNum);
			
			// DB자원 반납
			rdao.close();
			ldao.close();
			
			if (result == 1) {
				JSFunction.alertLocation(resp, "댓글이 삭제되었습니다!", req.getContextPath() + "/board/view.do?boardNum=" + boardNum);
				return;
			} else {
				JSFunction.alertBack(resp, "댓글 삭제에 실패했습니다!");
			}
		}
		
	}
}
