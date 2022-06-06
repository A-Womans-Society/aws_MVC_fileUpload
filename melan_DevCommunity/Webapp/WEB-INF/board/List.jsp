<%@ page import="reply.ReplyDAO"%>
<%@ page import="board.BoardDTO"%>
<%@ page import="board.BoardDAO"%>
<%@ page import="utils.BoardPage"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DevCommunity</title>
	<style>a{text-decoration:none;}</style>
</head>
<body>
	<!-- 공통 링크 -->
	<jsp:include page="../common/Link.jsp"/>
	<h1 align="center">👨‍💻<ins>DevCommunity</ins>👩‍💻</h1>
	<h2 align="center">📋 개발공부 질의응답 게시판(QnA)</h2>
	
	<table align="center">
		<tr>
			<td align="center">
				더 이상 500을 그만 보고 싶으시다구요??
				왜 에러가 나는건지 도저히 모르시겠다구요?? <br/>
				그렇다면 잘 오셨어요! 여기서 고민들을 나눕시다~ 😜 <br/>
				모든 글은 자유롭게 보실 수 있지만, 글쓰기 및 댓글쓰기 기능을 이용하시려면 <strong>로그인</strong>하셔야 합니다!
			</td>
		</tr>
	</table>
	<hr>
	<!-- 검색 폼 -->
	<form method="get">
		<table border="1" width="90%" align="center">
			<tr>
				<td align="center">
					<select name="searchField">
						<option value="title">제목</option>
						<option value="content">내용</option>
						<option value="memId">작성자 아이디</option>
					</select>
					<input type="text" name="searchWord"/>
					<input type="submit" value="검색"/>
				</td>
			</tr>
		</table>
	</form>

	<!-- 목록 테이블 -->
	<table border="1" width="90%" align="center">
		<tr>
			<th width="10%">No.</th>
			<th width="*">제목</th>
			<th width="15%">작성자 아이디</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
			<th width="10%">첨부파일 <br/>미리 다운로드</th>
		</tr>
		<c:choose>
			<c:when test="${ empty boardLists }"> <!-- 게시물이 없을 때 -->
				<tr>
					<td colspan="6" align="center">
						등록된 게시물이 없습니다ㅠ_ㅠ
					</td>
				</tr>
			</c:when>
			<c:otherwise> <!-- 게시물이 있을 때 -->
				<c:forEach items="${ boardLists }" var="row" varStatus="status">
				<tr align="center">
					<td> <!-- No. -->
						${ map.totalCount - (((map.pageNum-1) * map.pageSize) + status.index) }
					</td>
					<td align="left"> <!-- 제목(링크) -->
						<a href="./view.do?boardNum=${ row.boardNum }">${ row.title } (${ row.repcount }) </a>
					</td>
					<td>${ row.memId }</td> <!--작성자 아이디-->
					<td>${ row.visitcount }</td> <!--조회수 -->
					<td>${ row.postdate }</td> <!--작성일 -->
					<td> <!--첨부파일 미리 다운로드 -->
						<c:if test="${ not empty row.ofile }">
							<a href="./download.do?ofile=${ row.ofile }&sfile=${ row.sfile }&boardNum=${ row.boardNum }">[다운로드]</a>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
	
	<!-- 하단메뉴(바로가기, 글쓰기) -->
	<table border="1" width="90%" align="center">
		<tr align="center">
			<td>
				${ map.pagingImg }			
			</td>
			<td width="100">
				<button type="button" onclick="location.href='./write.do';">글쓰기</button>
			</td>
		</tr>
	</table>
</body>
</html>