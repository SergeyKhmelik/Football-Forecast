package web.servlet;

import entity.client.MatchInfo;
import entity.db.Championship;
import entity.db.Match;
import entity.db.Season;
import entity.db.Team;
import org.apache.log4j.Logger;
import service.OverviewService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MatchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MatchServlet.class);
    private static final String ERROR_IN_REQUEST = "reqParameterError";
    private static final String ERROR_IN_REQUEST_MESSAGE = "Requested parameters for season or championship are not valid.";

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

        int idChampionship = Integer.parseInt(req.getParameter("champ"));
        int idSeason = Integer.parseInt(req.getParameter("season"));

        try {
            Championship championship = overviewService.findChampionship(idChampionship);
            Season season = overviewService.findSeason(idSeason);
            if(season.getIdSeason() == 0 && championship.getIdChampionship() == 0){
                req.setAttribute(ERROR_IN_REQUEST, ERROR_IN_REQUEST_MESSAGE);
                req.getRequestDispatcher("matchesjsp").forward(req, resp);
            } else {
                List<MatchInfo> matches = overviewService.findMatches(idChampionship, idSeason);
                List<Team> teams = overviewService.findTeams();
                req.setAttribute("championship", championship);
                req.setAttribute("season", season);
                req.setAttribute("matches", matches);
                req.setAttribute("teams", teams);
                req.getRequestDispatcher("matchesjsp").forward(req, resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "addMatch":
                    addMatch(req, resp);
                    break;
                case "updateMatch":
                    updateMatch(req, resp);
                    break;
                case "deleteMatch":
                    deleteMatch(req, resp);
                    break;
                default:
            }
        } catch (ParseException e) {
            //TODO exception add
        } catch (SQLException e) {
            //TODO exception add
        }
    }

    private void deleteMatch(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int idMatch = Integer.parseInt(req.getParameter("idMatch"));
        overviewService.deleteMatch(idMatch);
        resp.setContentType("text/plain");
        resp.getWriter().write("Match has been successfully deleted.");
    }

    private void updateMatch(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int idMatch = Integer.parseInt(req.getParameter("idMatch"));
        int homeGoals = Integer.parseInt(req.getParameter("homeScore"));
        int guestGoals = Integer.parseInt(req.getParameter("guestScore"));
        Date date = sdf.parse(req.getParameter("date"));
        Match match = new Match();
        match.setIdMatch(idMatch);
        match.setHomeGoals(homeGoals);
        match.setGuestGoals(guestGoals);
        match.setDate(new java.sql.Date(date.getTime()));
        if(validateMatchOnGoals(match)) {
            overviewService.updateMatch(match);
            resp.setContentType("text/plain");
            resp.getWriter().write("Match has been successfully updated.");
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().write("Team can not have negative amount of goal scored.");
        }
    }

    private void addMatch(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, IOException {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int idHomeTeam = Integer.parseInt(req.getParameter("homeTeam"));
        int idGuestTeam = Integer.parseInt(req.getParameter("guestTeam"));
        int idChampionship = Integer.parseInt(req.getParameter("championship"));
        int idSeason = Integer.parseInt(req.getParameter("season"));
        Date date = sdf.parse(req.getParameter("date"));
        Match match = new Match();
        match.setIdChampionship(idChampionship);
        match.setIdSeason(idSeason);
        match.setIdHomeTeam(idHomeTeam);
        match.setIdGuestTeam(idGuestTeam);
        match.setDate(new java.sql.Date(date.getTime()));
        if(match.getIdHomeTeam() == match.getIdGuestTeam()){
            resp.setContentType("text/plain");
            resp.getWriter().write("Team can not be home and guest team at the same time.");
        } else {
            overviewService.addMatch(match);
            resp.setContentType("text/plain");
            resp.getWriter().write("Match has been successfully added.");
        }
    }

    private boolean validateMatchOnGoals(Match match){
        if(match.getGuestGoals() < 0 || match.getHomeGoals() < 0){
            return false;
        }
        return true;
    }
}
