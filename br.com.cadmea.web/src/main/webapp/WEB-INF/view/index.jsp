<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="header.jsp"/>
</head>

<body>
    <div id="wrap">
        <div class="container">
        	<div class="row">
        		<jsp:include page="menu.jsp"/>
        	</div>
        	<div class="row">
				<c:if test="${not empty page}">
				   <jsp:include page="${page}.jsp" />
				</c:if>
        	</div>
        </div>
        <jsp:include page="footer.jsp"/>  
    </div>
</body>
</html>
