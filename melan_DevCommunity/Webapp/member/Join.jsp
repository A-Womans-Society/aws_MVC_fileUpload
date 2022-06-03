<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
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
		var tfMemId = $('#memId').val();
		console.log(tfMemId);
		$.ajax({
			type: 'get',
			url: './idCheck.do?memId='+tfMemId
		}).done(function(result) {
			console.log(result);
			if (result == 0) {
				alert("중복된 아이디입니다!");
			} else if (result == 1) {
				isCheckedMemId = true;
				alert("사용 가능한 아이디입니다!^^");
				$("#memId").prop('readonly', true);
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
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">📝 회원가입</h1>
	<hr/>
	<h3 align="center">회원가입을 하시면 ✍ 글쓰기 기능을 사용하실 수 있습니다!</h3>
	<form action="join.do" name="joinForm" method="post" onsubmit="return validate(this)">
		<table align="center">
		    <tr><td align="center" width="100px">아이디</td>
			    <td>
			    	<input type="text" name="memId" id="memId" placeholder="id" required/>
			    	<button type="button" onclick="memIdCheck();">중복확인</button>
			    </td>
		    </tr>
		    <tr><td align="center">비밀번호<br/>(4자리 숫자)</td>
		    	<td><input type="password" name="memPwd" id="memPwd" placeholder="password" maxlength="4" required/></td>
		    </tr>		
		</table>
		<table align="center">
		    <tr><td>
		    <input type="submit" value="가입하기"> 
		    <input type="reset" value="다시입력">
    		</td></tr>
		</table>
	</form>
	
	
</body>
</html>