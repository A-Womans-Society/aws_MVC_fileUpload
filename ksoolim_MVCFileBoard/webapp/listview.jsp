<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% request.setCharacterEncoding("UTF-8");%>
<% response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>

<b>글목록(전체글:${count})</b>
<hr color=grey>
<table class= "listwritebotton">
	<tr>
	<td><a href = "writeform.jsp">글쓰기</a></td>	
	</tr>
</table>

<c:if test="${count == 0}">
<table border="1">
<td>게시물이 존재하지 않습니다.</td>
</table>
</c:if>

<c:if test="${count != 0}">

<table border="1">
	<tr>
		<th id="num">번  호</th>
		<th id="subject">제  목</th>
		<th id="writer">작성자</th>
		<th id="regdate">작성일</th>
		<th id="readcount">조회수</th>
			</tr>
<c:forEach var="list" items="${articleList}">
	<tr> 
	<td>${list.num}</td>
	<td class = "titled">
	<a href = "content?num=${list.num}">${list.subject}</a>
	<c:if test="${list.readcount } >= 10">
	<img src="images/hot.gif"></c:if></td>
	<td>${list.writer}</td>
	<td>${list.regdate}</td>
	<td>${list.readcount}</td>
		
</c:forEach>
</table>
</c:if>
</body>
</html>