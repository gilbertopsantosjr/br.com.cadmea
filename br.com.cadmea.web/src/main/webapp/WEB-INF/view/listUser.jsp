<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row" style="text-align: right;">
	<a href="/admin/formUser">New</a>
</div>

<table class="table">
	<thead>
		<tr>
			<th>#</th>
			<th>Nickname</th>
			<th>Email</th>
			<th>Date Register</th>
			<th>Update</th>
			<th>Exclude</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<th scope="row">${user.id}</th>
				<td>${user.nickname}</td>
				<td>${user.email}</td>
				<td><fmt:formatDate pattern="dd-MM-yyyy" value="${user.dateRegister}" /></td>
				<td>
					<a href="/admin/updateUser/${user.id}">
						<button type="button" class="btn btn-success">Update</button>
					</a>
				</td>
				<td>
					<a href="/admin/excludeUser/${user.id}">
						<button type="button" class="btn btn-danger">Exclude</button>
					</a>	
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>