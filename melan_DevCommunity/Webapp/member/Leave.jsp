<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 탈퇴</title>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">회원 탈퇴</h1>
	<hr>
	
	<form name="leaveForm" method="post" action="leave.do">
		<table align="center">
			<tr>
				<td align="center">정말 탈퇴하시겠습니까?</td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="yes"></td>
			</tr>
		</table>
	</form>
</body>
</html>