<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form:form id="userForm" action="/admin/saveUser" method="post" modelAttribute="userSystem">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#user">User</a></li>
		<li><a data-toggle="tab" href="#person">Person</a></li>
		<li><a data-toggle="tab" href="#contacts">Contacts</a></li>
	</ul>
	
	<div class="tab-content">
		<div class="tab-pane active" id="user">
			<br />
			<ul>
			<c:forEach items="${mgs}" var="ms">
				<li>${ms}</li>
			</c:forEach>
			</ul>
			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="nickname">Nickname:</label>
				<div class="col-sm-8">
					<form:input path="nickname" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="nickname"  /> </small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="email">Email:</label>
				<div class="col-sm-8">
					<form:input path="email" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="email"  /></small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="password">Password:</label>
				<div class="col-sm-8">
					<form:password path="password" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="password" /> </small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="dateExpire">Expire date:</label>
				<div class="col-sm-8">
					<form:input type="date" path="dateExpire" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="dateExpire" /> </small>
				</div>
			</div>

		</div>
		
		<div class="tab-pane" id="person">
			<br />

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="personName">Name:</label>
				<div class="col-sm-8">
					<form:input path="person.name" id="personName" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="person.name" /> </small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="nickname">Gender:</label>
				<div class="col-sm-8">
					<form:select cssClass="form-control" path="person.gender" items="${genderList}" />
					<small class="error text-danger"> <form:errors path="person.gender" /> </small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="nickname">Relationship:</label>
				<div class="col-sm-8">
					<form:select cssClass="form-control" path="person.relationship" items="${relationshipList}" />
					<small class="error text-danger"> <form:errors path="person.relationship" /> </small>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-sm-3 col-form-label" for="nickname">Date of birth:</label>
				<div class="col-sm-8">
					<form:input type="date" path="person.dateOfBirth" cssClass="form-control" />
					<small class="error text-danger"> <form:errors path="person.dateOfBirth" /> </small>
				</div>
			</div>

		</div>
		
		<div class="tab-pane" id="contacts">
			<br />
			<c:forEach items="person.emailAsList" var="email" varStatus="loop" >
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="email${loop.index}">Email:</label>
					<div class="col-sm-8">
						<form:input id="email${loop.index}" path="person.emailAsList[${loop.index}].address" cssClass="form-control" />
						<small class="error text-danger"><form:errors path="person.emailAsList[${loop.index}].address" /></small>
					</div>
				</div>
			</c:forEach>	
			
			<c:forEach items="person.phoneAsList" var="phone" varStatus="loop" >
				<div class="form-group row">
					<label class="col-sm-3 col-form-label" for="phone${loop.index}">Phone:</label>
					<div class="col-sm-8">
						<form:input id="phone${loop.index}" path="person.phoneAsList[${loop.index}].number" cssClass="form-control" />
						<small class="error text-danger"> <form:errors path="person.phoneAsList[${loop.index}].number" /> </small>
					</div>
				</div>
			</c:forEach>
		</div>
		
		<div class="modal-footer">
			<a href="/admin/listUser">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</a>
			<button type="submit" class="btn btn-default">Save</button>
		</div>
	</div>
</form:form>