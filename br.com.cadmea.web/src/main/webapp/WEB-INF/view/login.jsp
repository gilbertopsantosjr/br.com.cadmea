<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en" >
<head>
	<jsp:include page="header.jsp"/>
	
	<spring:url value="/resources/css" var="css" htmlEscape="true" />
	<spring:url value="/resources/js" var="js" htmlEscape="true" />
	
	<link href="${css}/login/login.css" rel="stylesheet" />
	<link href="${css}/auth-buttons.css" rel="stylesheet" />
	
</head>
<body>
    
<header class="b2dfdb teal lighten-4">
    <hgroup>
	  <h3>Cadmea System</h3>
	</hgroup>
</header>

<main ng-app="cadmea">

 	<div class="row center">
 	
		<a class="btn-auth btn-facebook large" href="/social/facebook/signin">
   			Sign in with <b>Facebook</b>
		</a>
		
		<a class="btn-auth btn-google large" href="#">
   			Sign in with <b>Google </b>
		</a>
	</div>
	
	<hr/>
	
	<div class="row">
	
		<form class="col s12" ng-controller="loginController as login" ng-submit="verify();" name="formulario">
	  	 	<div class="formLogin">
	  	 	
		  	 	<input type="text" ng-show="false"  id="systemName" ng-init="login.systemName = '1CADS'"  ng-model="login.systemName"  />
		  	 	
		  	 	<div class="row">
			  	 	<div class="input-field">
			          <i class="material-icons prefix">email</i>
			          <input id="email" ng-model="login.entity.email" type="email" class="validate">
			          <label for="email"  data-error="wrong" data-success="right">Email</label>
			        </div>
		  	 	</div>
		  	 	
	  	 		<div class="row">
			  	 	<div class="input-field">
			          <i class="material-icons prefix">lock_open</i>
			          <input id="password" ng-model="login.entity.password" type="password" class="validate">
			          <label for="password">Password</label>
			        </div>
			    </div>   
		        
		        <div class="row">
			        <div class="input-field">
				      <input type="checkbox" id="remember" />
				      <label for="remember">Remember me</label>
			  	 	</div>
			  	</div>	
	  	 	
	  	 	</div>
	  	 	
	  	 	<div class="row center">
		 		<button class="btn-auth btn-cadmea large" >
   					Sign in with <b>Cadmea </b>
				</button>
			</div>
			
			<div class="row center">
				<span> <a href="/forgot">Forgot Password ? </a> </span>
			</div>	
	 	 	
		</form>
	</div>	
		
</main>

<jsp:include page="footer.jsp" />
<script type="text/javascript" src="${js}/login/login.js"></script>
		
</body>
</html>
