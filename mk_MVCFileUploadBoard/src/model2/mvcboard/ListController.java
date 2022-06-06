package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// DAO 생성해 DB에 연결
		MVCBoardDAO dao = new MVCBoardDAO();
		// 뷰에 전달할 매개변수 저장용 맵 생성 - 사용자가 입력한 검색조건을 map에 저장
		Map<String, Object> map = new HashMap<String, Object>();

		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");

		if (searchWord != null) {
			// 쿼리 스트링으로 전달받은 매개변수 중 검색어가 있다면 map에 저장
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}

		int totalCount = dao.selectCount(map);// 게시물 개수

		// 페이지 처리 start
		ServletContext application = getServletContext(); // 뜻
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		// System.out.println("pagesize : " + pageSize);// 페이지당 게시물 수
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));// 블록당 페이지 수
				
		// 현재 페이지 확인
		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum");
		if (pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp); // 요청받은 페이지로 수정

		// 목록에 출력할 게시물 범위 계산 - ex) 현재 페이지 pageNum이 2이면 첫 게시물 6
		int start = (pageNum - 1) * pageSize + 1; // 첫 게시물 번호
		int end = pageNum * pageSize; // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		// 페이지 처리 end

		// 범위계산해서 매개변수 컬렉션에 추가하기
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		dao.close(); // db연결 닫기
		
		// 뷰에 전달할 매개변수 추가
		String pagingImg = BoardPage.pagingStr(
			totalCount, pageSize, blockPage, pageNum, "../mvcboard/list.do");

		// 바로가기 영역 html문자열
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);

		// 전달할 데이터를 request 영역에 저장 후 List.jsp로 포워드
		req.setAttribute("boardLists", boardLists); // 게시물 목록 저장
		req.setAttribute("map", map);
		req.getRequestDispatcher("/14MVCBoard/List.jsp").forward(req, resp);
	}
}
