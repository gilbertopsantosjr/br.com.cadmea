<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>Role</th>
      <th>Update</th>
      <th>Exclude</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${permissions}" var="per" >
    <tr>
      <th scope="row">1</th>
      <td>${per.role}</td>
      <td><a href="index.jsp?page=detailUser&?id=${user.id}">X</td>
      <td><a href="#?id=${user.id}">X</td>
    </tr>
   </c:forEach>
  </tbody>
</table>