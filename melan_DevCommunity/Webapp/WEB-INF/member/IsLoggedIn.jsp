<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.JSFunction" %>
<%
	if (session.getAttribute("userId") == null) {
		JSFunction.alertLocation("로그인 후 이용해주십시오.", request.getContextPath() + "/Login/LoginForm.jsp", out);
		return;
	}
%>