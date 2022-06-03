<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/board/write">글쓰기</a>
	<c:choose>
		<c:when test="${empty list}">
			<h1>목록이 없음</h1>
		</c:when>
		<c:otherwise>
			<table border="1">
				<tr>
					<th>No</th>
					<th>Writer</th>
					<th>Content</th>
					<th>File</th>
				</tr>
				<c:forEach var="dto" items="${list}" varStatus="status">
					<tr style="cursor: pointer;"
						onClick="location.href='detail.jsp?num=${dto.getNo()}'">
						<td>${status.count}</td>
						<td>${dto.getWriter() }</td>
						<td>${dto.getContent() }</td>
						<td>${dto.getFile() }</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>