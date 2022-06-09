<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
<section>
<b>글 내용 보기</b>
<hr color="grey">
<form>
	<table class="articletable" border="1">
	<tr>
		<th>글번호</th>
		<td>${article.num}</td>
		<th>조회수</th>
		<td>${article.readcount}</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${article.writer}</td>
		<th>작성일</th>
		<td>${article.regdate}</td>
	</tr>
	<tr>
		<th>글제목</th>
		<td colspan="3" class="articletitle"><pre>${article.subject }</pre></td>
	</tr>
	<tr>
		<th>글내용</th>
		<td colspan="3" class="article"><pre>${article.content }</pre></td>
	</tr>

	<tr>
		<td colspan="4">
			<input type="button" value="수정" onClick="location.href='${pageContext.request.contextPath}/getupdate?num=${article.num}'">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="삭제" onClick="location.href='${pageContext.request.contextPath}/delete?num=${article.num}'">
			 &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="목록" onClick="location.href='${pageContext.request.contextPath}/list'">
			
		</td>
	</tr>
	
	</table>
</form>

</section>
</body>
</html>