<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
</head>
<body>
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">로그인하세요 🤗</h1> 
	
	<%-- 내장 객체 영역에 LoginErrMsg 속성이 있는지 확인 후 그 내용 출력 --%>
	<span style="color: red; font-size: 1.2em;">
		<table align="center">
			<tr>
				<td>${requestScope.LoginErrMsg}</td>
			</tr>
		</table>
	</span>
	
	<form action="login.do" method="post" name="loginForm">
	<table align="center">
		<tr>
			<td>아이디</td>
			<td><input type="text" name="userId" placeholder="id" required/></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="userPwd" placeholder="password" required/></td>
		</tr>
		<tr align="center">
			<td colspan="2" align="center">
				<input type="submit" value="로그인"/>
				<input type="reset" value="초기화"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<hr>
				아직 회원이 아니신가요?<br/><a href="join.do">회원가입하러가기</a>
			</td>
		</tr>
	</table>
	</form>

</body>
</html>