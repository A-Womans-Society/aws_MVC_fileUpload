package action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDao;
import model.BoardDto;

public class ListAction implements CommandAction {
	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		String pageNum = req.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int pageSize = 10;
		int curPage = Integer.parseInt(pageNum);

		int startRow = (curPage - 1) * pageSize + 1;
		int endRow = curPage * pageSize;
		int count = 0;
		int number = 0;

		List<BoardDto> articleList = null;
		BoardDao dbPro = BoardDao.getInstance();
		count = dbPro.getArticleCount();
		if (count > 0) {
			articleList = dbPro.getArticles(startRow, endRow);
		} 
		number = count - (curPage - 1) * pageSize;

		// 해당 뷰에서 사용할 속성
		req.setAttribute("curPage", new Integer(curPage));
		req.setAttribute("startRow", new Integer(startRow));
		req.setAttribute("endRow", new Integer(endRow));
		req.setAttribute("count", new Integer(count));
		req.setAttribute("pageSize", new Integer(pageSize));
		req.setAttribute("number", new Integer(number));
		req.setAttribute("articleList", articleList);

		return "/WEB-INF/board/list.jsp";
	}
}
