<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<html>
<head>
    <jsp:include page="/WEB-INF/view/jspf/head.jspf">
        <jsp:param value="Welcome" name="title"/>
    </jsp:include>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>

<div class="container">

    <div id="content">
        <div class="row">
            <c:forEach var="championship" items="${requestScope.championships}">
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">
                        <img src="/FootballForecasts/img/${championship.name}.jpg">
                        <div class="caption">
                            <h4 class="text-center">${championship.name}</h4>
                            <p>${championship.description}</p>

                            <p>
                                <c:forEach var="season" items="${requestScope.seasons}">
                                    <a href="matches?champ=${championship.idChampionship}&season=${season.idSeason}" class="btn btn-default" role="button">Season<br/>${season.name}</a>
                                </c:forEach>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>

</body>
</html>
