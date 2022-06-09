<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script>
	function deleteSve(){
		if(document.delform.pass.value==""){
			alert("비밀번호를 입력하세요");
			document.delform.pass.focus();
			return false;
		}
	}
</script>
</head>
<body>
<section>
<b>정말 삭제하시겠습니까?</b>
<hr color="grey">

<form method="get" name="deleteform" action="${pageContext.request.contextPath}/deleteproc" >
<table>
	<tr>
		<td>비밀번호를 입력해주세요.</td>
	</tr>
	<tr>
		<td>비밀번호 :
			<input type="password" name="pass">
			<input type="hidden" name="num" value="${param.num}">
		</td>
	</tr>
	<tr>
		<td>
			<input type="submit" value="삭제">
			<input type="button" value="목록" onClick="location.href='${pageContext.request.contextPath}/list'">
</table>
</form>
</section>

</body>
</html>