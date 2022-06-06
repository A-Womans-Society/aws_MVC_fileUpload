<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<style>a{text-decoration:none;}</style>
</head>
<body>
<table border="1" width="100%">
	<tr>
		<td align="center" width="30"> <!-- 로그인 여부에 따른 메뉴 변화 -->
		<% if (session.getAttribute("userId") == null) { %>
			<a href="${pageContext.request.contextPath}/member/login.do">[로그인]</a>
		<% } else { %> 
			<mark><%= session.getAttribute("userId") %></mark> 님, 반갑습니다!
			<a href="${pageContext.request.contextPath}/member/logout.do">[로그아웃]</a>
		<% } %>
		</td>
		<td align="center" width="35%"> <!-- 게시판 홈으로 가기 -->
			<a href="${pageContext.request.contextPath}/board/list.do">게시판 홈(Board HOME)</a>
		</td>
		<td align="center" width="35%"> <!-- 마이페이지로 가기 -->
			<a href="${pageContext.request.contextPath}/member/mypage.do">마이페이지(My Page)</a>
		</td>
	</tr>
</table>
</body>
</html>
