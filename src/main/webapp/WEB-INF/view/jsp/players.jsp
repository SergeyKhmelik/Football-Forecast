<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<head>
    <jsp:include page="/WEB-INF/view/jspf/head.jspf">
        <jsp:param value="Welcome" name="title"/>
    </jsp:include>

    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
    <script type="text/javascript" src="js/players.js"></script>

    <style>
        .scrollable-menu {
            height: auto;
            max-height: 320px;
            overflow-x: hidden;
        }
    </style>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>

<div class="container">

    <div id="content">

        <div class="center-block text-center">
            <h3>${requestScope.match.homeTeam.name} - ${requestScope.match.guestTeam.name}</h3>

            <h5><fmt:formatDate type="date" dateStyle="medium" value="${requestScope.match.date}" /></h5>
        </div>

        <div class="row">
            <div class="col-sm-12 text-center">
                <h3>Lineup</h3>

                <div class="row">
                    <div class="col-sm-6">
                        <h4>${requestScope.match.homeTeam.name}</h4>

                        <c:choose>
                            <c:when test="${fn:length(requestScope.homeAddedPlayers) == 0}">
                                <hr/>
                                <h5>No players have been added yet. <br/>
                                    <button type="button" class="btn btn-link" data-toggle="modal" data-target="#addHomePlayers">
                                        Add ${requestScope.match.homeTeam.name} players
                                    </button>
                                </h5>
                                <%@include file="/WEB-INF/view/jspf/players/addHomePlayers.jspf"%>
                            </c:when>
                            <c:otherwise>
                                <%@include file="/WEB-INF/view/jspf/players/homeTable.jspf" %>
                            </c:otherwise>
                        </c:choose>
                    </div>


                    <div class="col-sm-6">
                        <h4>${requestScope.match.guestTeam.name}</h4>

                        <c:choose>
                            <c:when test="${fn:length(requestScope.guestAddedPlayers) == 0}">
                                <hr/>
                                <h5>
                                    No players have been added yet. <br/>
                                    <button type="button" class="btn btn-link" data-toggle="modal" data-target="#addGuestPlayers">
                                        Add ${requestScope.match.guestTeam.name} players
                                    </button>
                                </h5>
                                <%@include file="/WEB-INF/view/jspf/players/addGuestPlayers.jspf"%>
                            </c:when>
                            <c:otherwise>
                                <%@include file="/WEB-INF/view/jspf/players/guestTable.jspf" %>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>