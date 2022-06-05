<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="daeunBoard.BoardDao" %>
<%@ page import="daeunBoard.BoardVo" %>

<!DOCTYPE html>
<html>
<head>

<style>
form{padding-top: 30px; width:500px; margin: auto;
    left:0; top:0; right:0; bottom:0;}
table{border:1px solid #333; text-align:center;
    width:500px; height:100px; border:2px solid #333; border-collapse:collapse;}
</style>
<title>Content page</title>
<script src="${pageContext.request.contextPath}/script.js"></script>
</head>
<body>
<h2 style="text-align:center">글 내용 보기</h2>
<form>
	<table border="1">
		<tr>
			<th>글 번호</th>
			<td>${index}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${article.writer}</td>
		</tr>
		<tr>
			<th>제 목</th>
			<td>${article.title}</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td style="height:300px;"><pre>${article.content}</pre></td>
		</tr>
		<tr>
			<th>파 일</th>
		<c:choose>
			<c:when test="${article.file ne null}">
			<td> <a href="/daeun_MVCFileBoard/upload/${article.file}">${article.file}</a>
				<a href="/daeun_MVCFileBoard/upload/${article.file}" download>다운로드</a></td>
			</c:when>
			<c:otherwise>
			<td> 파일 없음
			</c:otherwise>
		</c:choose>
		</tr>
		</table>
		<br>
		<input type="button" value="글수정" onClick="document.location.href='<%=request.getContextPath()%>/board/update?num=${article.num}'">
		<input type="button" value="글삭제" onClick="deleteatc()">
		<input type="button" value="글목록" onClick="document.location.href='<%=request.getContextPath()%>/board/list'">
</form>
</body>

<script>
function deleteatc() {
	if(confirm("정말 삭제하시겠습니까?") == true) {
		location.href="${pageContext.request.contextPath}/board/delete?num=${article.num}";
		alert("삭제되었습니다.");
	}else {
		return;
	}
}
</script>
</html>