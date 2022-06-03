<%@ page import="member.MemberDTO"%>
<%@ page import="member.MemberDAO"%>
<%@ page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>마이페이지(MyPage)</title>
	<style>
		#memInfo {
	  		border : 1px solid black;
	  		border-collapse : collapse;
		};
	</style>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">🧡 ${requestScope.mdto.memId}님의 마이페이지 💜</h1>
	<hr/>
	<h2 align="center">🙋‍♀️ 회원 정보 🙋‍♂️</h2>
	<table align="center" id="memInfo">
		<tr>
			<td width="150px">회원 고유번호</td>	
			<td align="center">${mdto.memNum}</td>
		</tr>
		<tr>
			<td>아이디</td>	
			<td align="center">${requestScope.mdto.memId}</td>
		</tr>
		<tr>
			<td>비밀번호</td>	
			<td align="center">****</td>
		</tr>
		<tr>
			<td>회원 가입일자</td>	
			<td align="center">${requestScope.mdto.memRegidate}</td>
		</tr>
	</table>
	<br/>
	<h2 align="center">👉 마이페이지 메뉴 👈</h2>
	<table align="center">
		<tr>
			<td align="center">
				<input type="button" value="아이디 변경"onclick="location.href='./update.do?mode=id&memId=${requestScope.mdto.memId}';"/>
			</td>
		</tr>
		</tr>
		<tr>
			<td align="center">
				<input type="button" value="비밀번호 변경"onclick="location.href='./update.do?mode=pwd&memId=${requestScope.mdto.memId}';"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" value="내가 쓴 게시글 보기"onclick="location.href='../board/list.do?searchField=memId&searchWord=${requestScope.mdto.memId}';"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" value="회원 탈퇴"onclick="location.href='./leave.do';"/>
			</td>
		</tr>
	</table>
</body>
</html>