<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Administration</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href='<c:url value="/admin/listUser"/>'>Manager User</a></li>
      <li><a href='<c:url value="/admin/listPermission"/>'>Manger Permissions</a></li>
    </ul>
  </div>
</nav>