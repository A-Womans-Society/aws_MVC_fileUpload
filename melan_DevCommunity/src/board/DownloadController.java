package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.JSFunction;

@SuppressWarnings("serial")
@WebServlet("/board/download.do")
public class DownloadController extends HttpServlet{
	
	//private final String prefix = "../WEB-INF";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext application = getServletContext();
		String saveDirectory = application.getRealPath("/WEB-INF/Uploads");
		System.out.println("saveDirectory : " + saveDirectory);
		String saveFilename = req.getParameter("sfile");
		String originalFilename = req.getParameter("ofile");
		String boardNum = req.getParameter("boardNum");
		
		try {
			// 파일을 찾아 입력 스트림 생성
			File file = new File(saveDirectory, saveFilename);
			InputStream fin = new FileInputStream(file);
			
			// 한글 파일명 깨짐 방지(원본 파일명을 바이트배열로 변환 후 ISO-8859-1 캐릭터셋의 문자열로 재생성)
			String client = req.getHeader("User-Agent");
			if (client.indexOf("WOW64") == -1) { // 웹 브라우저가 IE가 아닌 경우
				originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
			} else { // 웹 브라우저가 IE인 경우 
				originalFilename = new String(originalFilename.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			// 파일 다운로드용 응답 헤더 설정
			resp.reset();
			resp.setContentType("application/octet-stream"); // 8비트 단위의 바이너리 데이터 -> WB가 파일 종류에 상관없이 다운로드 창을 띄움
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\"");
			// WB에서 파일 다운로드 창이 뜰 때 원본 파일명이 기본으로 입력되어 있도록 설정
			resp.setHeader("Content-Length", "" + file.length() );
			
			// 새로운 출력 스트림 생성하기 위해 출력 스트림 초기화
			//out.clear();
		
			// repsonse 내장객체로부터 새로운 출력 스트림 생성
			OutputStream os = resp.getOutputStream();
			
			// 출력 스트림에 파일 내용 출력
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while ((readBuffer = fin.read(b)) > 0) {
				os.write(b, 0, readBuffer);
			}
			
			// 입/출력 스트림 닫음
			fin.close();
			os.close();
			
			// 해당 게시물의 다운로드 수 1 증가
			BoardDAO dao = new BoardDAO();
			dao.downCountPlus(boardNum);
			dao.close();
			
		} catch (FileNotFoundException e) {
			JSFunction.alertBack(resp, "파일을 찾을 수 없습니다!");
		} catch (Exception e) {
			JSFunction.alertBack(resp, "파일 다운로드 중 예외가 발생했습니다!");
		}
	}
}
