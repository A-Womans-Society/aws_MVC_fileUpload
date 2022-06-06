<%@page import="reply.ReplyDTO"%>
<%@page import="reply.ReplyDAO"%>
<%@ page import="utils.JSFunction"%>
<%@ page import="board.BoardDTO"%>
<%@ page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>댓글 수정</title>
</head>
<body>

	<form action="../reply/edit.do?repNum=${ param.repNum }" method="post">
		<table width="70%" align="center">
			<tr align="center"> <!--댓글 작성자 아이디-->
				<td> 댓글 수정 아이디 </td>
				<td>${ param.memId }</td>	
			</tr>
			<tr> <!--댓글 내용-->
				<td colspan="2">
					<textarea name="content" style="width:100%;height:200px;" required>
					<%	ReplyDAO rdao = new ReplyDAO(); 
					String repNum = (String)request.getParameter("repNum");
					ReplyDTO rdto = rdao.getRdto(repNum);
					//rdto.setContent(rdto.getContent().replaceAll("\r\n", "<br/>")); %>
						<%= rdto.getContent() %>
					</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">댓글 수정 완료</button>
					<button type="reset">초기화</button>
					<button type="button" onclick="location.href ='../board/view.do?boardNum=<%= rdto.getBoardNum()%>';">댓글 수정 취소</button>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>