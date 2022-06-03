<%@ page import="board.BoardDAO"%>
<%@ page import="java.io.FileNotFoundException"%>
<%@ page import="utils.JSFunction"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.FileInputStream"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String saveDirectory = application.getRealPath("/Uploads");
	String saveFilename = request.getParameter("sfile");
	String originalFilename = request.getParameter("ofile");
	String boardNum = request.getParameter("boardNum");
	
	try {
		// 파일을 찾아 입력 스트림 생성
		File file = new File(saveDirectory, saveFilename);
		InputStream fin = new FileInputStream(file);
		
		// 한글 파일명 깨짐 방지(원본 파일명을 바이트배열로 변환 후 ISO-8859-1 캐릭터셋의 문자열로 재생성)
		String client = request.getHeader("User-Agent");
		if (client.indexOf("WOW64") == -1) { // 웹 브라우저가 IE가 아닌 경우
			originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
		} else { // 웹 브라우저가 IE인 경우 
			originalFilename = new String(originalFilename.getBytes("KSC5601"), "ISO-8859-1");
		}
		
		// 파일 다운로드용 응답 헤더 설정
		response.reset();
		response.setContentType("application/octet-stream"); // 8비트 단위의 바이너리 데이터 -> WB가 파일 종류에 상관없이 다운로드 창을 띄움
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\"");
		// WB에서 파일 다운로드 창이 뜰 때 원본 파일명이 기본으로 입력되어 있도록 설정
		response.setHeader("Content-Length", "" + file.length() );
		
		// 새로운 출력 스트림 생성하기 위해 출력 스트림 초기화
		out.clear();
		
		// repsonse 내장객체로부터 새로운 출력 스트림 생성
		OutputStream os = response.getOutputStream();
		
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
		JSFunction.alertBack("파일을 찾을 수 없습니다!", out);
	} catch (Exception e) {
		JSFunction.alertBack("예외가 발생했습니다!", out);
	}
	
%>