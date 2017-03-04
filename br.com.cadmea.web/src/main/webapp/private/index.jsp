<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en" ng-app="cadmea">
<head>
	<jsp:include page="/WEB-INF/view/header.jsp"/>
</head>

<body>
    <div id="wrap">
        <div class="container">
        	<div class="row">
        		<jsp:include page="/WEB-INF/view/menu.jsp"/>
        	</div>
        	<div class="content" ng-view>
				
        	</div>
        </div>
        <jsp:include page="/WEB-INF/view/footer.jsp"/>  
    </div>
</body>
</html>
