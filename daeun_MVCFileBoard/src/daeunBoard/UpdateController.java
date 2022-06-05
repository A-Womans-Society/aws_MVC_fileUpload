package daeunBoard;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/board/update")
public class UpdateController extends HttpServlet {
	BoardVo article = new BoardVo();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int num = Integer.parseInt(req.getParameter("num"));
		BoardDao dbPro = BoardDao.getInstance();
		BoardVo article = dbPro.listOne(num);
		req.setAttribute("article", article);
		req.getRequestDispatcher("/WEB-INF/views/updateForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		article.setRegDate(new Timestamp(System.currentTimeMillis()));
		req.setCharacterEncoding("UTF-8");
		String savePath = "/upload";
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		String encType = "UTF-8";
		String fileName = null;
		ServletContext context = req.getSession().getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(req, uploadFilePath, uploadFileSizeLimit, encType,
					new DefaultFileRenamePolicy());

			fileName = multi.getFilesystemName("uploadFile");

			String writer = multi.getParameter("writer");
			String content = multi.getParameter("content");
			String title = multi.getParameter("title");
			Timestamp regDate = article.getRegDate();

			String uploadFile = fileName;

			// update
			BoardDao dbPro = BoardDao.getInstance();
			int num = Integer.parseInt(req.getParameter("num"));
			if (fileName != null) {
				dbPro.update(title, content, uploadFile, num);
			} else {
				dbPro.updateNofile(title, content, num);
			}

			// send
			resp.sendRedirect(req.getContextPath() + "/board/list");

		} catch (Exception e) {
			System.out.println("예외 발생 : " + e);
			e.printStackTrace();
		}
	}
}
