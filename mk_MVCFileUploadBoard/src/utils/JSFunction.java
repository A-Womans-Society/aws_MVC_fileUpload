package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSFunction {
	//메시지 알림창을 띄운 후 명시한 URL로 이동
	public static void alertLocation(HttpServletResponse resp, String msg,
			String url) {
		try {
			resp.setContentType("text/html:charset=UTF-8"); //서블릿에서 즉시 내용을 출력하려면 type지정
			PrintWriter writer = resp.getWriter(); // 객체 필요하니 get으로 얻어옴
			String script = "" //하나의 문자열로 만든 후
					+ "<script>"
					+"alert('" + msg + "');"
					+" location.href='" + url + "';"
					+ "</script>";
				writer.print(script); //서블릿에서 즉시 출력
		}
		catch(Exception e) {}								
	}
		//메시지 알림창을 띄운 후 이전 페이지로 돌아감
	public static void alertBack(HttpServletResponse resp, String msg) {
		try {
			resp.setContentType("text/html:charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			String script = ""
					+ "<script>"
					+" alert('" + msg + "');"
					+" history.back();"
					+ "</script>";
				writer.print(script);
		}
		catch(Exception e) {}								
	}
}
