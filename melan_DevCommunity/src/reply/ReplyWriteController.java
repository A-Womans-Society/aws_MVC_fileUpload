package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet("/reply/write.do")
public class ReplyWriteController extends HttpServlet{
	
	//private final String prefix = "../WEB-INF";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		String boardNum = req.getParameter("boardNum");
		String memNum = session.getAttribute("userNum").toString();
		//String memId = session.getAttribute("userId").toString();
		String content = req.getParameter("content");
		
		// DTO 생성하고 값 채우기
		ReplyDTO rdto = new ReplyDTO();
		rdto.setBoardNum(boardNum);
		rdto.setMemNum(memNum);
		rdto.setContent(content);
				
		// DAO를 통해 DB에 반영
		ReplyDAO rdao = new ReplyDAO();
		int result = rdao.insertReply(rdto);
		rdao.close();
		
		if (result != 1) { // 댓글 등록 실패 시 
			JSFunction.alertBack(resp, "댓글 등록에 실패했습니다!ㅠㅠ");
		} else { // 댓글 등록 성공 시 게시글 상세보기 페이지로 리다이렉션
			JSFunction.alertLocation(resp, "댓글 등록에 성공했습니다!>_<", req.getContextPath() + "/board/view.do?boardNum=" + boardNum);
		}
			

	}
}
