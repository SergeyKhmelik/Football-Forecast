<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<head>
    <jsp:include page="/WEB-INF/view/jspf/head.jspf">
        <jsp:param value="Welcome" name="title"/>
    </jsp:include>
     <link rel="stylesheet" type="text/css" media="screen"
          href="css/jquery.dataTables.css"/>

    <%@include file="/WEB-INF/view/jspf/footer.jspf" %>
    <script type="text/javascript" src="js/matches.js"></script>
    <script type="text/javascript" src="js/external/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {
            $('#myTable').dataTable();
        });
    </script>


</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>

<div class="container">
    <div id="content">
        <!-- Отображать матчи, которые пришли с сервлета в таблице -->

        <jsp:useBean id="now" class="java.util.Date"/>

        <c:choose>
            <c:when test="${fn:length(requestScope.matches) == 0 }">
                <h3>${requestScope.championship.name} Matches</h3>
                <p>Season ${requestScope.season.name} (${requestScope.season.start} - ${requestScope.season.end})</p>
                <h5>No matches in this championship and season yet.</h5>
                <%@include file = "/WEB-INF/view/jspf/popup/addMatch.jspf" %>
            </c:when>

            <c:otherwise>
                <h3>${requestScope.championship.name} Matches</h3>
                <p>Season ${requestScope.season.name} (${requestScope.season.start} - ${requestScope.season.end})</p>
                <hr/>
                <div class="table-responsive">
                    <table id="myTable" class="display table" width="100%">
                        <caption>
                        </caption>
                        <thead>
                        <tr>
                            <th class="text-center">Date</th>

                            <th class="text-center">Home</th>

                            <th class="text-center">Home score</th>

                            <th class="text-center">Guest score</th>

                            <th class="text-center">Guest</th>

                            <th class="text-center">Action /
                                <%@include file = "/WEB-INF/view/jspf/popup/addMatch.jspf" %>
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="match" items="${requestScope.matches}">
                            <c:choose>
                                <c:when test="${match.date gt now}">
                                    <tr id="matchRow${match.idMatch}" class="success text-center">
                                </c:when>
                                <c:otherwise>
                                    <tr id="matchRow${match.idMatch}" class="text-center">
                                </c:otherwise>
                            </c:choose>
                            <td>${match.date}</td>
                            <td>${match.homeTeam.name}</td>
                            <td>${match.homeGoals}</td>
                            <td>${match.guestGoals}</td>
                            <td>${match.guestTeam.name}</td>
                            <td>
                                <button class="btn btn-link" onclick="updateMatch(${match.idMatch})">Update</button>
                                <button class="btn btn-link" onclick="deleteMatch(${match.idMatch})">Delete</button>
                                <a class="btn btn-link" href="players?match=${match.idMatch}">Players</a>

                                <!-- <button class="btn btn-link" onclick="forecastMatch(${match.idMatch})">Forecast</button> -->

                                <form action="forecast" method="post">
                                    <input type="hidden" name="idMatch" value="${match.idMatch}" />

                                    <input type="submit" class="btn btn-link" value="Forecast" />
                                </form>
                            </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>

        <%@include file = "/WEB-INF/view/jspf/popup/updateMatch.jspf" %>

    </div>
</div>


</body>
</html>