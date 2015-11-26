package web.servlet;

import entity.PlayersPositionComparator;
import entity.client.MatchInfo;
import entity.db.Match;
import entity.db.MatchPlayer;
import entity.db.Player;
import org.apache.log4j.Logger;
import service.OverviewService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayersServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PlayersServlet.class);

    private OverviewService overviewService;

    @Override
    public void init() throws ServletException {
        overviewService = (OverviewService) getServletContext().getAttribute("overviewService");

        if (overviewService == null) {
            LOGGER.error("Could not get services from application context");
            throw new UnavailableException(
                    "Could not get overview service.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        int idMatch = Integer.parseInt(req.getParameter("match"));

        try {
            MatchInfo match = overviewService.findMatch(idMatch);

            List<Player> homeTeamPlayersAdded = overviewService.findAddedPlayers(match.getIdMatch(), match
                    .getHomeTeam().getIdTeam());
            List<Player> homeTeamPlayersNotAdded = overviewService.findNotAddedPlayers(match.getIdMatch(), match
                    .getHomeTeam().getIdTeam());
            List<Player> guestTeamPlayersAdded = overviewService.findAddedPlayers(match.getIdMatch(), match
                    .getGuestTeam().getIdTeam());
            List<Player> guestTeamPlayersNotAdded = overviewService.findNotAddedPlayers(match.getIdMatch(), match
                    .getGuestTeam().getIdTeam());


            Collections.sort(homeTeamPlayersAdded, new PlayersPositionComparator());
            Collections.sort(homeTeamPlayersNotAdded, new PlayersPositionComparator());
            Collections.sort(guestTeamPlayersAdded, new PlayersPositionComparator());
            Collections.sort(guestTeamPlayersNotAdded, new PlayersPositionComparator());


            req.setAttribute("match", match);
            req.setAttribute("homeAddedPlayers", homeTeamPlayersAdded);
            req.setAttribute("homeNotAddedPlayers", homeTeamPlayersNotAdded);
            req.setAttribute("guestAddedPlayers", guestTeamPlayersAdded);
            req.setAttribute("guestNotAddedPlayers", guestTeamPlayersNotAdded);

            req.getRequestDispatcher("playersjsp").forward(req, resp);

        } catch (SQLException e) {
            //TODO exception add
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "replace":
                    replacePlayer(req, resp);
                    break;
                case "add":
                    addPlayers(req, resp);
                    break;
                default:
            }
        } catch (SQLException e) {
            //TODO exception add
            e.printStackTrace();
        }
    }

    private void addPlayers(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String playersString = req.getParameter("players");
        int idMatch = Integer.parseInt(req.getParameter("match"));
        ArrayList<MatchPlayer> players = parsePlayers(playersString, idMatch);
        if (validatePlayersAdd(players)) {
            overviewService.addPlayers(players);
            resp.setContentType("text/plain");
            resp.getWriter().write("Players have been successfully added.");
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("You should add 11 players.");
        }
    }

    private void replacePlayer(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int idMatch = Integer.parseInt(req.getParameter("match"));
        int idPlayer = Integer.parseInt(req.getParameter("player"));
        int idReplacer = Integer.parseInt(req.getParameter("replacer"));
        overviewService.replacePlayer(idMatch, idPlayer, idReplacer);
        resp.setContentType("text/plain");
        resp.getWriter().write("Player has been successfully replaced.");
    }

    private ArrayList<MatchPlayer> parsePlayers(String playersString, int idMatch) {
        ArrayList<MatchPlayer> players = new ArrayList<MatchPlayer>();
        Pattern pattern = Pattern.compile("(\\d+)\\s*");
        Matcher matcher = pattern.matcher(playersString);
        while (matcher.find()) {
            MatchPlayer current = new MatchPlayer();
            current.setIdMatch(idMatch);
            current.setIdPlayer(Integer.parseInt(matcher.group(1)));
            players.add(current);
        }
        return players;
    }

    private boolean validatePlayersAdd(ArrayList<MatchPlayer> players) {
        if (players.size() != 11) {
            return false;
        }
        return true;
    }

}
