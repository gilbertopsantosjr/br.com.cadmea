<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<h2>
Hi, 
<c:out value="${userSystem.entity.person.name}" />
<c:out value="${userSystem.entity.person.surname}" />
</h2>

last visited : <c:out value="${userSystem.entity.lastVisit}" /> 

<c:out value="${userSystem.entity.email}" />
<br/>
<img src="<c:out value="${userSystem.pictureProfile}" />"  />
