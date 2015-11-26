<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<jsp:include page="/WEB-INF/view/jspf/head.jspf">
  <jsp:param name="title" value="UserManagement" />
</jsp:include>

<%@include file="/WEB-INF/view/jspf/footer.jspf" %>

<style>
  th {
    text-align: center;
  }
</style>

<script type="text/javascript">
  function block(idUser) {
    $.post('users', {action: "block", idUser: idUser}, function(responseText) {
      alert(responseText);
      window.location.reload(true);
    });
  };
</script>

</head>
<body>
<jsp:include page="/WEB-INF/view/jspf/header.jspf"></jsp:include>

<div class="container">

  <div id="content">

    <h4>
      Clients
    </h4>

    <table class="table table-hover">
      <thead>
      <tr>
        <th>Login</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</th>
        <th>Blocked</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${requestScope.users}">
        <tr>
          <td>${user.login}</td>
          <td>${user.name}</td>
          <td>${user.surname}</td>
          <td>${user.email}</td>
          <td>${user.blocked}</td>
          <td>
            <button class="btn btn-link" onclick="block(${user.idUser})">Block/Unblock user</button>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </div>
  </div>
</body>
</html>