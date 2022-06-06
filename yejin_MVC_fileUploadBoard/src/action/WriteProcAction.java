package action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.jsp.PageContext;

import model.BoardDao;
import model.BoardDto;

public class WriteProcAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");
		String contentType = req.getContentType();
		if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			BoardDao dao = BoardDao.getInstance();
			
			String writer = req.getParameter("writer");
			String email = req.getParameter("email");
			String title = req.getParameter("title");
			String pass = req.getParameter("pwd");
			int readcount = 0;
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String content = req.getParameter("content");
			String fileName = printPartInfo(req, resp);
			
			BoardDto dto = new BoardDto(writer, email, title, pass, readcount, time, content, fileName);
			dao.upload(dto);

		} else {
			System.out.println("multipart가 아님");
		}
		resp.sendRedirect(req.getContextPath()+"/board/");
	}

	private String printPartInfo(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Collection<Part> parts = req.getParts();
		String fileName = "";
		for (Part part : parts) {
			String contentType = part.getContentType();
			if (contentType == null) {
				part.delete();
			} else {
				long size = part.getSize();
				System.out.println("size: " + size);
				fileName = getFileName(part);
				if (size > 0) {
					part.write("C:\\uploadFiles\\" + fileName);
					part.delete();
				}
			}
		}
		return fileName;
	}

	private String getFileName(Part part) {
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String tmp = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				tmp = tmp.substring(tmp.indexOf(":") + 1);
				return tmp;
			}
		}
		return null;
	}

}
