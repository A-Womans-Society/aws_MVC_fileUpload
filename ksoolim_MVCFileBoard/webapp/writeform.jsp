<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Board</title>
<script src="script.js"></script>
</head>

<body>
<section>
<b>글쓰기</b>
<article>
	<form method="post" name="writeForm" action="write" onsubmit="return writeSave()">
	
	<table class="board">
		<tr>
			<td class="attr">이름</td>
			<td><input type="text" name="writer"/></td>
		</tr>
		<tr>
			<td class="attr">제목</td>
			<td>
				<input class="input" type="text" name="subject"/>
			</td>
		</tr>
		<tr>
			<td class="attr">내용</td>
			<td>
				<textarea name="content" rows="13" cols="50"></textarea>
			</td>
		</tr>
		<tr>
			<td class="attr">비밀번호</td>
			<td><input type="password" name="pass"/></td>
		</tr>
		<tr>
			<td colspan="2" class="attr">
				<input type="submit" value="글쓰기"/>
				<input type="reset" value="다시 작성"/>
				<input type="button" value="목록" onClick="location.href='${pageContext.request.contextPath}/list'">
			</td>
		</tr>
	</table>
</form>
</article>
</section>
</body>
</html>
