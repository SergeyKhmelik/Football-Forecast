<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<head>
    <jsp:include page="/WEB-INF/view/jspf/head.jspf">
        <jsp:param value="Forecast" name="title"/>
    </jsp:include>

    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>

</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>

<div class="container">

    <div id="content">
        <div class="center-block text-center">
            <h3>Prediction for match ${requestScope.prediction.matchInfo.homeTeam.name}
                - ${requestScope.prediction.matchInfo.guestTeam.name} <br/>
                (<fmt:formatDate type="date" dateStyle="medium" value="${requestScope.prediction.matchInfo.date}"/>)
            </h3>
        </div>
        <br/>

        <h4>${requestScope.prediction.matchInfo.homeTeam.name} team win chance
            = ${requestScope.prediction.homeTeamChance}
            <br/>
            <br/>
            ${requestScope.prediction.matchInfo.guestTeam.name} team win chance
            = ${requestScope.prediction.guestTeamChance}
        </h4>
    </div>
</div>
</body>