package board;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.MemberDAO;
import member.MemberDTO;
import reply.ReplyDAO;
import reply.ReplyDTO;

@WebServlet("/board/view.do")
public class ViewController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 불러오기
		BoardDAO bdao = new BoardDAO();
		String boardNum  = req.getParameter("boardNum");
		bdao.updateVisitCount(boardNum);
		BoardDTO dto = bdao.selectView(boardNum);
		bdao.close();
		
		// 댓글 불러오기
		ReplyDAO rdao = new ReplyDAO();
		MemberDAO mdao = new MemberDAO();
		
		Map<ReplyDTO, String> replyMap = new LinkedHashMap<>();
		
		List<ReplyDTO> replyList = rdao.replyListByBN(boardNum);
		Iterator<ReplyDTO> it = replyList.iterator();
		while (it.hasNext()) {
			ReplyDTO rdto = it.next();
			rdto.setContent(rdto.getContent().replaceAll("\r\n", "<br/>")); // 댓글 내용 줄바꿈 처리
			MemberDTO mdto = mdao.getMdtoByNum(rdto.getMemNum()); // 회원 아이디 가져오기
			System.out.println("댓글 아이디가져옴 : " + mdto.getMemId());
			replyMap.put(rdto, mdto.getMemId()); // 회원 댓글dto, 아이디 map에 추가
		}
		
		rdao.close();
		mdao.close();
		
		// 게시물 내용 줄바꿈 처리
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		req.setAttribute("dto", dto); // 게시물 저장
		//req.setAttribute("replyList", replyList); // 댓글 리스트 저장
		req.setAttribute("replyMap", replyMap);
		
		req.getRequestDispatcher("./View.jsp").forward(req, resp);

		
	}
}
