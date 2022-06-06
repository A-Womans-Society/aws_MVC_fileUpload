<%@ page import="utils.JSFunction" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원제 파일첨부 게시판 - 글쓰기</title>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	
	<h1 align="center">게시판에 글쓰기</h1>
	<form name="writeForm" method="post" enctype="multipart/form-data" action="write.do">
		<table border="1" width="90%" align="center">
			<tr>
				<td width="15%" align="center">작성자 아이디</td>
				<td><%= session.getAttribute("userId") %></td>
			</tr>
			<tr>
				<td align="center">제목</td>
				<td><input type="text" name="title" style="width:95%;" required/></td>
			</tr>
			<tr>
				<td align="center">내용</td>
				<td><textarea name="content" style="width:95%;height:200px;" required></textarea></td>	
			</tr>
			<tr>
				<td align="center">첨부 파일(1개만 가능)</td>
				<td><input type="file" name="attachedFile"/></td>	
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성 완료</button>
					<button type="reset">초기화</button>
					<button type="button" onclick="location.href='list.do';">목록으로 돌아가기</button>
				</td>
			</tr>			
		</table>
	</form>
</body>
</html>