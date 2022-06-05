<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<html>
<head>
<style>
body {
	padding-top: 30px;
	width: 700px;
	margin: auto;
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
}

table {
	width: 700px;
	border-collapse: collapse;
}

tr {
	text-align: center;
	border: 1px solid #333;
}
</style>
<title>list page</title>
<script src="${pageContext.request.contextPath}/script.js"></script>

</head>
<body>
	<h2 style="text-align: center">자 료 실</h2>
	<b>글 목록 (전체 글: ${count})</b>

	<c:choose>
		<c:when test="${count eq 0}">
			<table>
				<tr>
					<td>게시판에 저장된 글이 없습니다.</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<form name="articleList">
				<table border="1">
					<th>선 택</th>
					<th>번 호</th>
					<th>작성자</th>
					<th>제 목</th>
					<th>작성일</th>
					<th>파일 이름</th>
					<th>조회수</th>

					<c:forEach var="article" items="${article_list}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="checkdelete"
								value="${article.num}"></td>
							<td>${fn:length(article_list) - status.index}</td>
							<td style="cursor: pointer"
								onClick="location.href='content?num=${article.num}&index=${fn:length(article_list) - status.index}'">${article.writer}</td>
							<td style="cursor: pointer"
								onClick="location.href='content?num=${article.num}&index=${fn:length(article_list) - status.index}'">${article.title}</a></td>
							<td style="cursor: pointer"
								onClick="location.href='content?num=${article.num}&index=${fn:length(article_list) - status.index}'"><fmt:formatDate
									value="${article.regDate}" pattern="yyyy-MM-dd HH:mm" /></td>
							<td style="cursor: pointer"
								onClick="location.href='content?num=${article.num}&index=${fn:length(article_list) - status.index}'">${article.file}</td>
							<td>${article.readCnt}</td>
						</tr>
					</c:forEach>
				</table>
		</c:otherwise>
	</c:choose>
	<table>
		<tr>
			<td style="text-align: right;">
				<button type="button" onClick="selDelete()">삭제</button> 
				<button type="button" onClick="window.location='<%=request.getContextPath()%>/board/write'">글쓰기</button>
			</td>
		</tr>
	</table>
	</form>
</body>
<script>
	function selDelete() {
		var arr = document.getElementsByName("checkdelete");
		var chk = false;
		var num = 0;
		var lst = [];
		if (arr.length > 0) {
			for (var i = 0; i < arr.length; i++) {
				if (arr[i].checked == true) {
					chk = true;
					lst.push(arr[i].value);
				}
			}
		}
		if (lst.length > 0) {
			location.href = "${pageContext.request.contextPath}/board/delete?num="+ lst;
		} else {
			alert("삭제할 항목을 선택해주세요.");
		}
	}
</script>
</html>