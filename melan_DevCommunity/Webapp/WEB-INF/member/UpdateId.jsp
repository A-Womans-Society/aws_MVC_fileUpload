<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 정보 수정</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
	<script>
	var isCheckedMemId = false;
	function validate() {
		if(!isCheckedMemId) {
			alert("아이디 중복확인을 해주세요!");
		}
		return isCheckedMemId;
	}
	function memIdCheck() {
		var newId = $('#newId').val();
		console.log(newId);
		$.ajax({
			type: 'get',
			url: './idCheck.do?mode=update&newId='+newId
		}).done(function(result) {
			console.log(result);
			if (result == 0) {
				alert("중복된 아이디입니다!");
			} else if (result == 1) {
				isCheckedMemId = true;
				alert("사용 가능한 아이디입니다!^^ 이 아이디를 사용하시겠습니까? 아니라면 ESC를 누르세요!");
				$("#newId").prop('readonly', true);
			} else if (result == 2) {
				alert("아이디를 먼저 입력해주세요!")
			} else {
				console.log("에러에러에러");
			}
		}).fail(function(error) {
			console.log(error);
		});
	}
	</script>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">아이디 변경</h1>
	<hr/>
	<h3 align="center"> 💡 아이디를 바꾸셔도 회원 고유번호는 바뀌지 않습니다! 💡 </h3>
	<form name="updateMemId" method="post" action="update.do?mode=id" onsubmit="return validate(this)">
		<table align="center">
			<tr>
				<td width="30%">기존 아이디</td>
				<td width="70%">${sessionScope.userId}</td>
			</tr>
			<tr>
				<td>신규 아이디</td>
				<td>
					<input type="text" name="newId" id="newId" placeholder="새로운 아이디를 입력하세요" required/>
					<button type="button" onclick="memIdCheck();">중복확인</button>
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