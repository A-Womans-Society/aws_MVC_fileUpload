<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 정보 수정</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">비밀번호 변경</h1>
	<hr/>
	<form name="updateMemPwd" method="post" action="update.do?mode=pwd">
		<table align="center">
			<tr>
				<td width="120px">기존 비밀번호</td>
				<td >
					<input type="password" name="oldPwd" id="oldPwd" placeholder="기존 비밀번호를 입력" required/>
				</td>
			</tr>
			<tr>
				<td>신규 비밀번호<br/>(숫자 4자리)</td>
				<td>
					<input type="password" name="newPwd" id="newPwd" placeholder="새로운 비밀번호를 입력" maxlength="4" required/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="변경하기"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>