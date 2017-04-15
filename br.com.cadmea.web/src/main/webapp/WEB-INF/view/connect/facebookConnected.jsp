<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<jsp:forward page="${pageContext.request.contextPath}/social/signin/facebook" />