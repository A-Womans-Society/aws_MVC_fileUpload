s<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<c:choose>
		<c:when test="${check == 1}">
			<% response.sendRedirect("./list"); %>
		</c:when>
		<c:otherwise>
			<b>비밀번호가 다릅니다.</b>
			<a href = "javascript:history.go(-1)">[이전으로 돌아가기]</a>
	</c:otherwise>
	</c:choose>

</body>
</html>
