package board;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JSFunction;

@WebServlet("/board/delete.do")
public class DeleteController extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") == null) {
			JSFunction.alertLocation(resp, "우선 로그인해주세요!", "../member/login.do");
			return;
		}
		String memId = req.getSession().getAttribute("userId").toString();
		String boardNum = req.getParameter("boardNum");
		BoardDAO bdao = new BoardDAO();
		BoardDTO bdto = bdao.selectView(boardNum);
		req.setAttribute("bdto",bdto);
		if (!bdto.getMemId().equals(memId)) { // 작성자 본인이 아니라면
			JSFunction.alertBack(resp, "작성자 본인만 삭제 가능합니다!");
			return;
		}
		
		int result = bdao.deletePost(boardNum); // 게시물 삭제
		bdao.close();
		if (result == 1 && bdto.getSfile() != null) { // 게시물 삭제 성공 시 첨부파일도 삭제
			String saveFileName = bdto.getSfile();
			String sDirectory = req.getServletContext().getRealPath("/Uploads");

			File file = new File(sDirectory + File.separator + saveFileName);
			if (file.exists()) {
				file.delete();
			} else {
				JSFunction.alertBack(resp, "삭제에 실패했습니다!");
			}
		} 
		JSFunction.alertLocation(resp, "삭제되었습니다!", "./list.do");
	}

}
