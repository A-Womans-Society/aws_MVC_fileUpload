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

@WebServlet("/board/write")
public class WriteController extends HttpServlet {
	BoardVo article = new BoardVo();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/writeForm.jsp").forward(req, resp);
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

			// insert
			BoardDao dbPro = BoardDao.getInstance();
			if (fileName != null) {
				dbPro.insertArticle(title, uploadFile, writer, content);

			} else {
				dbPro.insertNofile(title, writer, content);
			}

			// send
			resp.sendRedirect(req.getContextPath() + "/board/list");

		} catch (Exception e) {
			System.out.println("예외 발생 : " + e);
			e.printStackTrace();
		}
	}
}
