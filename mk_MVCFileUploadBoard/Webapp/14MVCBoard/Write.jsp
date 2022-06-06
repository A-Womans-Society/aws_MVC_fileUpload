<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script type="text/javascript">
<!-- 필수 항목 입력 확인 빈 값이 있는지를 확인하기 위해-->
	function validateForm(form){
		if(form.name.value == ""){
		alert("작성자를 입력하세요.");
		form.name.focus();
		return false;
		}
		if(form.title.value == ""){
		alert("제목을 입력하세요.");
		form.title.focus();
		return false;
		}
		if(form.content.value == ""){
		alert("내용를 입력하세요.");
		form.content.focus();
		return false;
		}
		if(form.pass.value == ""){
		alert("비밀번호를 입력하세요.");
		form.name.focus();
		return false;
		}		
	}
</script>
</head>
<body>
	<h2>파일 첨부형 게시판 -글쓰기</h2>
	<!--  작성완료가 되면 목록보기로 이동되어야 함. -->
	<!-- 검색 폼 = 여기서 입력된 검색어는 ListController 서블릿으로 전송된 후  select 메서드 인수로 전달됨 -->
	<form name ="writeFrm" method="post" enctype ="multipart/form-data"
	action = "../mvcboard/write.do" onsubmit ="return validateForm(this);">
<table border="1" width="90%">
		<tr>
			<td>작성자</td>
				<td>
				<input type ="text" name ="name" style="width:150px;"/>
				</td>
		</tr>
		<tr>
			<td>제목</td>
				<td>
				<input type ="text" name ="title" style ="width:90px;"/>
				</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<textarea name ="content" style ="width:90px;height:100px;"></textarea>
			</td>
		</tr>
		<tr>
			<td>첨부파일</td>
				<td>
				<input type ="file" name ="ofile"/>
				</td>
		</tr>
		<tr>
			<td>비밀번호</td>
				<td>
				<input type ="password" name ="pass" style ="width:100px;"/>
				</td>
		</tr>
		<tr>
				<td colspan ="2" align ="center">
				<button type = "submit" > 작성완료 </button> <!-- 폼값 전송 기능 -->
				<button type = "reset"> RESET</button>
				<button type = "button" onclick = "location.href='../mvcboard/list.do';">
				목록 바로가기
				</button>
				</td>
			</tr>
</table>
</form>
</body>
</html>
				