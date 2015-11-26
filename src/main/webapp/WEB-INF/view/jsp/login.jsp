<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<head>
    <jsp:include page="/WEB-INF/view/jspf/head.jspf">
        <jsp:param value="Welcome" name="title"/>
    </jsp:include>

    <link rel="stylesheet" type="text/css" media="screen"
          href="css/login.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#"
                                    <c:if test="${sessionScope.registrationError == null}">
                                        class="active"
                                    </c:if>
                               id="login-form-link">Login
                            </a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#"
                                    <c:if test="${sessionScope.registrationError != null}">
                                        class="active"
                                    </c:if>
                               id="register-form-link">Register</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <c:remove var="loginerror" scope="session"/>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">

                            <!-- LOGIN FORM -->
                            <form id="login-form" action="loginservlet" method="post" role="form"
                                    <c:choose>
                                        <c:when test="${sessionScope.registration == null}">
                                            style="display: block;">
                                        </c:when>
                                        <c:otherwise>
                                            style="display: none;">
                                        </c:otherwise>
                                    </c:choose>

                            <div class="form-group">
                                <input type="text" name="login" id="username" tabindex="1" class="form-control"
                                       placeholder="Username" value="">

                                <!-- ERRORS --> <span style="color: red;">${sessionScope.loginvalidation}</span>
                                <c:remove var="loginvalidation" scope="session"/>

                                <!-- ERRORS --> <span style="color: red;">${sessionScope.loginerror}</span>
                                <c:remove var="loginerror" scope="session"/>
                            </div>
                            <div class="form-group">
                                <input type="password" name="password" id="logpassword" tabindex="2"
                                       class="form-control" placeholder="Password">
                            </div>

                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="login-submit" id="login-submit" tabindex="4"
                                               class="form-control btn btn-login" value="Log In">
                                    </div>
                                </div>
                            </div>
                            </form>

                            <!-- REGISTER FORM -->
                            <form id="register-form" action="registration" method="post"
                                  role="form"
                                    <c:choose>
                                        <c:when test="${sessionScope.registration != null}">
                                            style="display: block;">
                                        </c:when>
                                        <c:otherwise>
                                            style="display: none;">
                                        </c:otherwise>
                                    </c:choose>
                            <span style="color: red;">${sessionScope.emptyFieldsError}<br/></span>
                                <c:remove var="emptyFieldsError" scope="session"/>


                            <span style="color: red;">${sessionScope.namingPatternError}</span>
                                <c:remove var="namingPatternError" scope="session"/>
                            <div class="form-group">
                                <input type="text" name="name" id="name" tabindex="1" class="form-control"
                                       placeholder="Name" value="">
                            </div>


                            <span style="color: red;">${sessionScope.namingPatternError}</span>
                                <c:remove var="namingPatternError" scope="session"/>
                            <div class="form-group">
                                <input type="text" name="surname" id="surname" tabindex="1" class="form-control"
                                       placeholder="Surname" value="">
                            </div>


                            <span style="color: red;">${sessionScope.loginPatternError}</span>
                                <c:remove var="loginPatternError" scope="session"/>
                            <span style="color: red;">${sessionScope.loginInUseError}</span>
                                <c:remove var="loginInUseError" scope="session"/>
                            <div class="form-group">
                                <input type="text" name="login" id="login" tabindex="1" class="form-control"
                                       placeholder="Login" value="">
                            </div>


                            <span style="color: red;">${sessionScope.passwordPatternError}</span>
                                <c:remove var="passwordPatternError" scope="session"/>
                            <div class="form-group">
                                <input type="password" name="password" id="regpassword" tabindex="2"
                                       class="form-control" placeholder="Password">
                            </div>


                            <span style="color: red;">${sessionScope.passwordConfirmationError}</span>
                                <c:remove var="passwordConfirmationError" scope="session"/>
                            <div class="form-group">
                                <input type="password" name="confirmPassword" id="confirm-password" tabindex="2"
                                       class="form-control" placeholder="Confirm Password">
                            </div>


                            <span style="color: red;">${sessionScope.emailPatternError}</span>
                                <c:remove var="emailPatternError" scope="session"/>
                            <span style="color: red;">${sessionScope.emailInUseError}</span>
                                <c:remove var="emailInUseError" scope="session"/>
                            <div class="form-group">
                                <input type="email" name="email" id="email" tabindex="1" class="form-control"
                                       placeholder="Email Address" value="">
                            </div>


                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit" name="register-submit" id="register-submit"
                                               tabindex="4" class="form-control btn btn-register"
                                               value="Register Now">
                                    </div>
                                </div>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
<script type="text/javascript" src="js/login.js"></script>

</body>
</html>
