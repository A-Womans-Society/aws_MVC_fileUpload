<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--  서블릿(컨트롤러)에서 내용을 request에 저장했으니 뷰에서 입력 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	}
</script>
</head>
<body>
<h2>파일 첨부형 게시판 -수정하기(Edit)</h2>
<form name ="writeFrm" method="post" enctype ="multipart/form-data"
	action = "../mvcboard/edit.do" onsubmit ="return validateForm(this);">
<!-- 서버에 저장된 파일명, 원본 파일명 전달 -->
<input type="hidden" name ="idx" value ="${ dto.idx }"/>
<input type="hidden" name ="prevOfile" value ="${ dto.ofile }"/>
<input type="hidden" name ="prevSfile" value ="${ dto.sfile }"/>
<table border="1" width="90%">
		<tr>
		<!-- 각 dto는 기존에 게시물의 내용으로 입력상자를 채움 -->
			<td>작성자</td>
				<td>
				<input type ="text" name ="name" style="width:150px;"
				value = "${dto.name}"/>
				</td>
		</tr>
		<tr>
			<td>제목</td>
				<td>
				<input type ="text" name ="title" style ="width:90px;"
				value = "${dto.title}"/>
				</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<textarea name ="content" style ="width:90px;height:100px;">
				${dto.content}</textarea>
			</td>
		</tr>
		<tr>
			<td>첨부파일</td>
				<td>
				<input type ="file" name ="ofile"/>
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