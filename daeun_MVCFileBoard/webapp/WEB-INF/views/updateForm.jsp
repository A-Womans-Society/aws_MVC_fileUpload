<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="daeunBoard.BoardDao" %>
<%@ page import="daeunBoard.BoardVo" %> 
 

<html>
<head>
<title>update page</title>
<script src="${pageContext.request.contextPath }/script.js"></script>
</head>
<body>
<h3 style="text-align:center">글 수정하기</h3>
<form method="post" name="updateForm" action="<%=request.getContextPath()%>/board/update?num=${article.num}"  
		enctype="multipart/form-data" onsubmit="updateSave()">
<table>
	<tr>
		<td>이름</td>
		<td><input type="text" name="writer" value="${article.writer}" disabled></td>
	</tr>
	<tr>
		<td>제목</td>
		<td><input type="text" name="title" value="${article.title}"></td>
	</tr>
	<tr>
		<td>내용 <br>(100자 이내)</td>
		<td>
			<textarea rows="15" cols="40" name="content">${article.content}</textarea>
		</td>
	</tr>
	<tr>
		<td>파일</td> 
		<td><input type="file" name="uploadFile"></td>
	</tr>
	<tr>
		<td colspan="2" >
		<br>
		<input type="submit" value="등록"/>
		<input type="reset" value="다시 작성"/>
		<input type="button" value="목록" onClick="window.location='<%=request.getContextPath()%>/board/list'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>