package board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BoardPage;

@SuppressWarnings("serial")
@WebServlet("/board/list.do")
public class ListController extends HttpServlet{

	private final String prefix = "../WEB-INF";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// DAO를 생성해 DB에 연결
		BoardDAO dao = new BoardDAO();
		
		// 사용자가 입력한 검색 조건을 Map에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		if (searchWord != null) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		// 페이징 처리
		int totalCount = dao.selectCount(map);
		map.put("totalCount", totalCount);
		
		ServletContext application = getServletContext();
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		int totalPage = (int)Math.ceil((double)totalCount / pageSize); // 전체 페이지 수
		map.put("pageSize", pageSize);
		map.put("blockPage", blockPage);
		map.put("totalPage", totalPage);
		
		int pageNum = 1; 
		String pageTemp = req.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals("")) {
			pageNum = Integer.parseInt(pageTemp); 
		}
		map.put("pageNum", pageNum);

		int start = (pageNum - 1) * pageSize + 1; // 첫 게시물 번호
		int end = pageNum * pageSize; // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "./list.do");
		map.put("pagingImg", pagingImg);
		
		// 게시물 목록 받기
		List<BoardDTO> boardLists = dao.selectListPage(map);
		dao.close();
		
		// 전달할 데이터를 request 영역에 저장 후 List.jsp로 포워드
		req.setAttribute("boardLists", boardLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher(prefix + "/board/List.jsp").forward(req, resp);
	}
}
