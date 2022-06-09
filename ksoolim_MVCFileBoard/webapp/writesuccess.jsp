<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
<form action="writeform.jsp">
<h3>작성하신 글이 등록되었습니다</h3>
<hr color="grey">
<input type="submit" value="글쓰기">
<input type="button" value="목록" onClick="location.href='${pageContext.request.contextPath}/list'">
</form>
</body>
</html>