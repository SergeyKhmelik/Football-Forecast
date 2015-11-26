// This function is anonymous, is executed immediately and
// the return value is assigned to QueryString!
var QueryString = function () {
    var query_string = {};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        // If first entry with this name
        if (typeof query_string[pair[0]] === "undefined") {
            query_string[pair[0]] = pair[1];
            // If second entry with this name
        } else if (typeof query_string[pair[0]] === "string") {
            var arr = [ query_string[pair[0]], pair[1] ];
            query_string[pair[0]] = arr;
            // If third or later entry with this name
        } else {
            query_string[pair[0]].push(pair[1]);
        }
    }
    return query_string;
} ();

//Add home player onclick() function. Sends idPlayer, using AJAX POST request.
$(document).ready(function(){
    $('#submitAddHPBtn').click(function(){
        var result = "";
        $('#addHomePlayers input[type=checkbox]').each(function () {
           if($(this).is(":checked")) {
               result = result + " " + $(this).attr("id");
           }
        });
        $.post(
            'players',
            {action: "add", players: result, match: QueryString.match},
            function(responseText) {
                alert(responseText);
                window.location.reload(true);
            }
        );
    });
});

//Add guest player onclick() function. Sends idPlayer, using AJAX post query.
//As a response function alerts responded text.
$(document).ready(function(){
    $('#submitAddGPBtn').click(function(){
        var result = "";
        $('#addGuestPlayers input[type=checkbox]').each(function () {
            if($(this).is(":checked")) {
                result = result + " " + $(this).attr("id");
            }
        });
        $.post(
            'players',
            {action: "add", players: result, match: QueryString.match},
            function(responseText) {
                alert(responseText);
                window.location.reload(true);
            }
        );
    });
});

//Replaces one player for another. Parameters: int idPlayer, int idReplacer. Sends AJAX post query.
//As a response function alerts responded text.
function replace(idPlayer, idReplacer) {
    $.post(
        'players',
        {action: "replace", player: idPlayer, replacer: idReplacer, match: QueryString.match },
        function(responseText) {
            alert(responseText);
            window.location.reload(true);
        }
    );
};