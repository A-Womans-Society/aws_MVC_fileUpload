package reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

@WebServlet(value = {"/reply/like.do"}, initParams = {
		@WebInitParam(name = "memNum", value = ""),
		@WebInitParam(name = "repNum", value = ""),
})
public class LikeController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String memNum = req.getParameter("memNum");
		String repNum = req.getParameter("repNum");
		System.out.println("memNum : " + memNum);
		System.out.println("repNum : " + repNum);
		
		ReplyLikesDTO ldto = new ReplyLikesDTO();
		ReplyDAO rdao = new ReplyDAO();
		ReplyLikesDAO ldao = new ReplyLikesDAO();
		
		int result = 0;
		if (memNum.equals(rdao.getMemNum(repNum))) { // 본인 댓글에는 공감 못함
			result = 3;
			resp.getWriter().print(result);
			//JSFunction.alertBack(resp, "본인 댓글에는 공감할 수 없습니다!");
			return;
		}		
		
		if (ldao.getLdto(memNum, repNum) != null) { // 해당 댓글에 공감표시를 했다면
			ldto = ldao.getLdto(memNum, repNum);
			System.out.println("지금 공감상태 : " + ldto.getTaste());
			if (ldto.getTaste().equals("like")) { // 이미 공감한 상태라면
				result = rdao.downLikeCount(repNum); // 비공감으로 바꾸기
				if (result == 1) {
					ldao.updateTaste(memNum, repNum, "hate"); // 개인공감테이블에도 반영
					result = 0; // 비공감 처리 완료
				} else {
					result = 2; // 새로고침 후 다시 시도
				}
			} else { // 비공감 상태라면
				result = rdao.upLikeCount(repNum); // 공감으로 바꾸기
				if (result == 1) {
					ldao.updateTaste(memNum, repNum, "like"); // 개인공감테이블에도 반영
					result = 1; // 공감 처리 완료
				} else {
					result = 2; // 새로고침 후 다시 시도
				}
			}
		} else { // 해당 댓글에 처음 공감표시를 한다면
			ReplyLikesDTO firstldto = new ReplyLikesDTO();
			
			firstldto.setMemNum(memNum);
			firstldto.setRepNum(repNum);
			firstldto.setTaste("like");
			
			ldao.insertLike(firstldto); // 개인공감테이블에 반영
			result = rdao.upLikeCount(repNum); // 댓글 rdto에도 반영
		}
		
		rdao.close();
		ldao.close();
		resp.getWriter().print(result);
	}

}
