package action;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class test extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=utf-8");

		String contentType = req.getContentType();
		System.out.println(contentType);
		if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			printPartInfo(req, resp);
		} else {
			System.out.println("저런.");
		}
	}

	private void printPartInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Collection<Part> parts = req.getParts();

		for (Part part : parts) {
			System.out.println("name : " + part.getName());
			System.out.println("ContentType : " + part.getContentType());
			String contentType = part.getContentType();
			if (contentType == null) {
				part.delete();
			} else if (contentType.startsWith("application/")) {
				long size = part.getSize();
				System.out.println("size: " + size);
				String fileName = getFileName(part);
				if (size > 0) {
					part.write("C:\\Users\\Administrator\\Desktop\\하예진\\test2" + fileName);
					part.delete();
				} else {
					
				}
			}
		}
	}

	private String getFileName(Part part) {
		System.out.println("getFileName() 시작");
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			System.out.println(cd.trim());
			if (cd.trim().startsWith("filename")) {
				String tmp = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				System.out.println("tmp: " + tmp);
				tmp = tmp.substring(tmp.indexOf(":") + 1);
				System.out.println("tmp: " + tmp);
				return tmp;
			}
		}
		return null;
	}
}
