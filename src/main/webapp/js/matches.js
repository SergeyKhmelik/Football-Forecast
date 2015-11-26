//Submits add match form
$(document).ready(function() {
    $("#submitAddBtn").click(function () {
        $.post('matches', $('#addMatchForm').serialize(), function (responseText) {
            alert(responseText);
            window.location.reload(true);
        });
    });
});

//Submits update match form
$(document).ready(function() {
    $("#submitUpdateBtn").click(function () {
        $.post('matches', $('#updateMatchForm').serialize(), function(responseText) {
            alert(responseText);
            window.location.reload(true);
        });
    });
});

//Deletes match, using int idMatch as a parameter
function deleteMatch(idMatch) {
    $.post('matches', {action: "deleteMatch", idMatch: idMatch}, function(responseText){
        alert(responseText);
        window.location.reload(true);
    });
};

//Calls update popup execution.
function updateMatch(idMatch) {
    var date = $('#matchRow' + idMatch).find("td:nth-child(1)").html();
    var homeTeamName = $('#matchRow' + idMatch).find("td:nth-child(2)").html();
    var homeGoals = $('#matchRow' + idMatch).find("td:nth-child(3)").html();
    var guestGoals = $('#matchRow' + idMatch).find("td:nth-child(4)").html();
    var guestTeamName = $('#matchRow' + idMatch).find("td:nth-child(5)").html();


    var header = "Updating match " + homeTeamName + "-" + guestTeamName;
    $('#myModalLabel').html(header);

    $('#updateMatchIdInput').attr("value", idMatch);

    $('#dateUpdateInput').attr("placeholder", date);
    $('#dateUpdateInput').attr("value", date);

    $('#homeTeamUpdateLable').html(homeTeamName);
    $('#guestTeamUpdateLable').html(guestTeamName);

    $('#homeGoalsInput').attr("placeholder", homeGoals);
    $('#homeGoalsInput').attr("value", homeGoals);

    $('#guestGoalsInput').attr("placeholder", guestGoals);
    $('#guestGoalsInput').attr("value", guestGoals);


    $('#updateModal').modal('toggle');
};

//Sends GET request for players.
function players(idMatch) {
    $.get( 'players', { match: idMatch});
};

//Sends POST request for the forecast.
function forecastMatch(idMatch) {
    $.post('forecast', { idMatch: idMatch});
};