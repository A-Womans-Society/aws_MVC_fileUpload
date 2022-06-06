<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>댓글 작성</title>
</head>
<body>
	<form name="writeReplyForm" method="post" action="../reply/write.do">
		<table width="70%" align="center">
			<tr><td colspan="2">
				<h3>&nbsp;댓글 쓰기</h3>
			</td></tr>
			<tr>
				<td>
					<input type="hidden" name="boardNum" value="${ param.boardNum }"/>
				</td>	
			</tr>
		</table>
		<table width="70%" align="center">
<!-- 			<tr> -->
<!-- 				<td align="center">작성자 아이디</td> -->
<%-- 				<td><%= session.getAttribute("userId") %></td> --%>
<!-- 			</tr> -->
			<tr>
				<td colspan="2"><textarea name="content" style="width:100%;height:200px;" required></textarea></td>	
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성 완료</button>
					<button type="reset">초기화</button>
					<button type="button" onclick="location.href='list.do';">목록으로 돌아가기</button>
				</td>
			</tr>			
		</table>
	</form>
</body>
</html>