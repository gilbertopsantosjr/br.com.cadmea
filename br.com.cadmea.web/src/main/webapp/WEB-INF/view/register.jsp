<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en" >
<head>
	<jsp:include page="header.jsp"/>
	
	<spring:url value="/resources/css" var="css" htmlEscape="true" />
	<spring:url value="/resources/js" var="js" htmlEscape="true" />
	
</head>
<body>
    
<header class="b2dfdb teal lighten-4">
    <hgroup>
	  <h3>Cadmea System</h3>
	</hgroup>
</header>

<main ng-app="cadmea">
	<div class="row">
		<form ng-controller="userController as user" ng-submit="save();">
			
			<div class="col s12">
      			<ul class="tabs">
      				<li class="tab col s6"><a class="active" href="#account">Account</a></li>
      				<li class="tab col s6"><a href="#person">Person</a></li>
      			</ul>
     		</div>
     		
			<div id="account" class="col s12">
				<jsp:include page="formUser.jsp" />
		  	</div>
		  	
		  	<div id="person" class="col s12">
		  		<jsp:include page="formPerson.jsp" />
		  	</div>
		  	
		  	<div class="row">
				<div class="input-field col s6 center">
					<input type="checkbox" id="readTerms" />
					<label for="readTerms">
						<a class="modal-trigger" data-target="readTerms">Read Terms</a>
					</label>
				</div>
				
				<div class="input-field col s6">
  					<button class="btn waves-effect waves-light" type="submit" name="action">Cancel
    					<i class="material-icons right">cancel</i>
  					</button>
  					<button class="btn waves-effect waves-light" type="submit" name="action">Submit
    					<i class="material-icons right">save</i>
  					</button>
				</div>
			</div>
			
		</form>
	</div>
</main>

<div id="readTerms" class="modal">
  <div class="modal-content">
    <h4>Modal Header</h4>
    <p>A bunch of text</p>
  </div>
  <div class="modal-footer">
    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Agree</a>
  </div>
</div>

<jsp:include page="footer.jsp" />
<script type="text/javascript" src="${js}/login/login.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
    	$('ul.tabs').tabs({ 'responsiveThreshold':true });
    	$('select').material_select();
    	$('#readTerms').modal();
  	});
</script>
</body>
</html>