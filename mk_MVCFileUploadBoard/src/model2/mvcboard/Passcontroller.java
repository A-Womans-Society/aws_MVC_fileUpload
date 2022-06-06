package model2.mvcboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fileupload.FileUtil;
import utils.JSFunction;

// 수정과 삭제 모두 pass.do를 사용하며 매개변수 중 mode 값만 다름.
// pass.do는 비밀번호를 확인하는 서블릿으로 연결될 것이으로, 수정의 경우에는 비밀번호 확인 후
// 수정하기 페이지로 이동하고, 삭제의 경우에는 비밀번호 확인이 완료되면 별도의 이동없이 게시물을 즉시 삭제함.
@WebServlet("/mvcboard/pass.do")
public class Passcontroller extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/14MVCBoard/Pass.jsp").forward(req, resp);
	}

	@Override // 비밀번호 입력폼에서 전송한 값을 받아 처리하므로 dopost 메서드에 작성하자
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매개변수 받아 변수에 저장하고
		String idx = req.getParameter("idx");
		String mode = req.getParameter("mode");
		String pass = req.getParameter("pass");

		// 비밀번호 확인 - dao를 통해
		MVCBoardDAO dao = new MVCBoardDAO(); //dao 객체 생성
		boolean confirmed = dao.confirmPassword(pass, idx); //dao를 통해 비밀번호가 맞는지 확인 
		dao.close();

		if (confirmed) { // 비밀번호와 일치하면
			if (mode.equals("edit")) {// 수정모드(현재 요청이 수정이라면)
				HttpSession session = req.getSession(); //session 영역에
				session.setAttribute("pass", pass); // 비밀번호를 저장한 후 
				resp.sendRedirect("../mvcboard/edit.do?idx =" + idx); // 수정하기 페이지로 이동

			} else if (mode.equals("delete")) { // 삭제모드(현재 요청이 삭제라면, 첨부된 파일도 같이 삭제해야 함)
				dao = new MVCBoardDAO();
				MVCBoardDTO dto = dao.selectView(idx); // 게시물을 삭제하기 전에 기존 정보를 보관해뒀다가
				int result = dao.deletePost(idx); // 게시물 삭제, 삭제 후에
				dao.close();

				if (result == 1) {// 게시물 삭제 성공 시 첨부파일도 삭제
					String saveFileName = dto.getSfile(); // 보관해둔 정보에서 파일 이름을 찾아
					FileUtil.deleteFile(req, "/Uploads", saveFileName);//첨부파일까지 마저 삭제
				}
				JSFunction.alertLocation(resp, "삭제되었습니다.", "../mvcboard/list.do"); // 마무리 되었다면 목록 페이지로 이동
			}
		} else { // 비밀번호 불일치라면 경고창으로 이전페이지 이동
			JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
		}
	}

}
