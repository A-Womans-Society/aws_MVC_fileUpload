<%@page import="member.MemberDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="reply.ReplyDTO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>댓글 목록</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
	<script>
	function likeReload(){ // like 부분만 새로고침
	      $('#likeArea2').load(location.href+' #likeArea2');
	}
	function upDown(repNum) {
		var memNum = "${sessionScope.userNum}"; 
		//var repNum = $('#likeBtn').data("value");
		if (memNum === "") {
			alert("로그인 후 공감하실 수 있습니다! 😅 ");
			console.log(memNum);
			window.location.href = '../member/login.do';
		} else {
			console.log(memNum);
			console.log(repNum);
			$.ajax({
				type: 'get',
				url: '../reply/like.do?memNum='+memNum+'&repNum='+repNum
			}).done(function(result) {
				if (result == 0) { // 비공감 처리 성공
					alert("공감을 취소했습니다 👎 ");
					//likeReload();
					location.reload();
					return;
				} else if (result == 1) { // 공감 처리 성공
					alert("공감을 표시했습니다 👍 ");
					//likeReload();
					location.reload();
					return;
				} else if (result == 2){ // 공감/비공감 처리 실패
					alert("새로고침 후 다시 시도해주세요 🙏 ");
					return;
				} else if (result == 3) {
					alert("본인 댓글에는 공감할 수 없습니다!");
					return;
				}
			}).fail(function(error) {
				alert("처리에 실패했습니다! 😅 ");
				console.log(error);
				location.reload();
				return;
				//window.location.href = '../member/login.do';
			});	
		}
	}
	</script>
</head>
<body>
	<table width="70%" align="center">
		<tr>
			<td>
				<h3>&nbsp;댓글 ${replyMap.size()}개</h3>
			</td>
		</tr>
	</table>
	
		<!-- 댓글 목록 테이블 -->
	<table border="1" width="70%" align="center">
		<tr>
			<th width="*%"> 작성자 아이디 </th>
			<th width="30%"> 작성일자 </th>
			<th width="30%"> 수정일자 </th>
			<th width="15%"> 공감 수💛 </th>
		</tr>
		<c:choose>
			<c:when test="${ empty replyMap.size() }"> <!-- 댓글이 없을 때 -->
				<tr>
					<td colspan="6" align="center">
						등록된 댓글이 없습니다ㅠ_ㅠ
					</td>
				</tr>
			</c:when>
			<c:otherwise> <!-- 댓글이 있을 때 -->
				<c:forEach items="${ replyMap }" var="entry" varStatus="status">				
				<tr align="center">
				
					<td>${ entry.value }</td> <!--댓글 작성자 아이디-->
					<td>${ entry.key.createDate }</td> <!-- 댓글 작성일자 -->
					<td> <!-- 댓글 수정일자 -->
						<c:if test="${ not empty entry.key.updateDate }">
							${ entry.key.updateDate }
						</c:if>
	
					</td>
					<td> <!-- 좋아요 갯수(버튼 클릭 시 repNum값 인자로 보내서 처리) -->
						 <div id="likeArea">
						 <button id="likeBtn" onclick="upDown(${entry.key.repNum})">${ entry.key.likeCount }</button>
						 </div>
					</td>
				
				</tr>
				<tr>
					<td colspan="4">${ entry.key.content }</td> <!--댓글 내용-->
				</tr>
				<tr>
					<td colspan="4" align="right">  <!--삭제 버튼(댓글 작성자 본인에게만 보임)-->
						<c:if test="${ entry.value eq sessionScope.userId }">
							<button id="delBtn" onclick="location.href='../reply/delete.do?repNum=${entry.key.repNum}';">댓글 삭제</button>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>

	
</body>
</html>