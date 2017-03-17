<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<h2>
Hi, 
<c:out value="${first_name}"></c:out>
<c:out value="${last_name}"></c:out>
</h2>

<c:out value="${email}" />
<br/>
<img src="<c:out value="${picture}" />"  />
