<%@ include file="/WEB-INF/view/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf"%>
<html>

<jsp:include page="/WEB-INF/view/jspf/head.jspf">
  <jsp:param name="title" value="PermissionManagement" />
</jsp:include>
<link rel="stylesheet" type="text/css" media="screen"
      href="css/left_tabs.css"/>


<body>
<jsp:include page="/WEB-INF/view/jspf/header.jspf"></jsp:include>
<div class="container">
  <div id="content">
    <div class="tabbable tabs-left">
      <ul class="nav nav-tabs">
        <c:forEach var="role" items="${requestScope.roles}">
          <c:choose>
            <c:when test="${role.idRole eq requestScope.roles[0].idRole}">
              <li class="active"><a href="#${role.name}" data-toggle="tab">${role.name}</a></li>
            </c:when>
            <c:otherwise>
              <li><a href="#${role.name}" data-toggle="tab">${role.name}</a></li>
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </ul>
      <div class="tab-content">
        <c:forEach var="role" items="${requestScope.roles}">
          <c:choose>
            <c:when test="${role.idRole eq requestScope.roles[0].idRole}">
              <div class="tab-pane fade in active" id="${role.name}">
            </c:when>
            <c:otherwise>
              <div class="tab-pane fade" id="${role.name}">
            </c:otherwise>
          </c:choose>
          <div class="container">
            <!--center-->
            <div class="col-sm-10">
              <p>${role.description}</p>

              <c:if test="${mlib:containsPermission(sessionScope.permissions, 'Update permissions') eq true}">
                <div class="dropdown">
                  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                    Add permission
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                    <c:forEach var="missingPermission" items="${role.missingPermissions }">
                      <li role="presentation">
                        <form action="permissions" method="post">
                          <input type="hidden" name="action" value="add" />
                          <input type="hidden" name="idRole" value="${role.idRole }" />
                          <input type="hidden" name="idPermission" value="${missingPermission.idPermission }" />
                          <input type="submit" value="${missingPermission.name}"
                                 role="menuitem" tabindex="-1" class="btn btn-link"  alt="${missingPermission.description }" />
                        </form>
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </c:if>

              <c:forEach var="permission" items="${role.permissions}">
                <div class="row">
                  <hr/>
                  <div class="col-sm-10 text-center">
                    <h3>${permission.name}</h3>
                    <p>${permission.description}</p>
                    <br/>
                    <form action="permissions" method="post">
                      <input type="hidden" name="action" value="delete" />
                      <input type="hidden" value="permission" name="object"/>
                      <input type="hidden" value="${role.idRole}" name="idRole"/>
                      <input type="hidden" value="${permission.idPermission}" name="idPermission"/>
                      <c:if test="${mlib:containsPermission(sessionScope.permissions, 'Update permissions') eq true}">
                        <button type="submit" class="btn btn-default">Remove</button>
                      </c:if>
                    </form>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
      </div>
      </c:forEach>
    </div>
  </div>
</div>
</div>

<jsp:include page="/WEB-INF/view/jspf/footer.jspf"></jsp:include>
</body>
</html>
