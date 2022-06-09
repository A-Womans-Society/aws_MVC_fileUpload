<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<script src="script.js"></script>
</head>
<body>
<section>
<b>글 수정하기</b>
<hr color="grey">

<form method="post" name="writeForm" action="${pageContext.request.contextPath}/update?num=${article.num}" onsubmit="return writeSave()">

	<table class="board" border="1">
		<tr>
			<td class="attr">이름</td>
			<td>${article.writer}
			<input type="hidden" name="writer" value="${article.writer }">
			<input type="hidden" name="num" value="${article.num }"></td>
		</tr>
		<tr>
			<td class="attr">제목</td>
			<td>
			<input class="input" type="text" name="subject" value="${article.subject}"/>
			</td>
		</tr>
		<tr>
			<td class="attr">내용</td>
			<td>
				<textarea name="content" rows="13" cols="50">${article.content}</textarea>
			</td>
		</tr>
		<tr>
			<td class="attr">비밀번호</td>
			<td><input type="password" name="pass"/></td>
		</tr>
		
		<tr>
			<td colspan="2" class="attr">
				<input type="submit" value="수정완료"/>
				<input type="reset" value="다시 작성"/>
				<input type="button" value="목록" onClick="document.location.href=listview.jsp">
			</td>
		</tr>
	</table>
</form>
</section>

</body>
</html>
