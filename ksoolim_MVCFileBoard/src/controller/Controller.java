package controller;


import java.io.IOException;
import java.util.HashMap;

import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
	private Map<String, Command> map = new HashMap<>();

	@Override
	public void init() throws ServletException {
		map.put("/list", new ListController());
		map.put("/write", new WriteController());
		map.put("/content", new ContentController());
		map.put("/delete", new DeleteController());
		map.put("/deleteproc", new DeleteProcController());
		map.put("/getupdate", new GetUpdateController());
		map.put("/update", new UpdateProcController());
	}

	/*
	 * String contextConfigFile = this.getInitParameter("controller.properties");
	 * Properties properties = new Properties(); FileInputStream fis = null;
	 * 
	 * try { String contextConfigFilePath =
	 * this.getServletContext().getRealPath(contextConfigFile); fis = new
	 * FileInputStream(contextConfigFilePath); properties.load(fis); } catch
	 * (IOException e) { e.printStackTrace(); } finally { if (fis != null) { try {
	 * fis.close(); } catch (IOException e) { e.printStackTrace(); } } }
	 * 
	 * Iterator<Object> propIt = properties.keySet().iterator(); while
	 * (propIt.hasNext()) { String command = (String) propIt.next(); String
	 * controllerClassName = properties.getProperty(command);
	 * 
	 * try { Class<?> controllerClass = Class.forName(controllerClassName);
	 * map.put(command, (Command) controllerClass.newInstance()); } catch
	 * (ClassNotFoundException e) { e.printStackTrace(); } catch
	 * (IllegalAccessException e) { e.printStackTrace(); } catch
	 * (InstantiationException e) { e.printStackTrace(); } }
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		System.out.println(req.getMethod());
		String uri = req.getRequestURI();
		System.out.println("uri:" + uri);
		String ctxPath = req.getContextPath();
		System.out.println("ctxPath:" + ctxPath);
		String cmd = uri.substring(ctxPath.length());
		System.out.println("cmd:" + cmd);
		String prefix = "/";
		String suffix = ".jsp";
		String viewPage = "";
		try {
			Command handler = map.get(cmd);
			if (cmd.equals("/")) {
				viewPage = "index";
			} else if (handler == null) {
				resp.sendRedirect(req.getContextPath());
				return;
			} else {
				viewPage = handler.process(req, resp);
			}
			System.out.println("뷰 페이지:" + prefix + viewPage + suffix);
			req.getRequestDispatcher(prefix + viewPage + suffix).forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
