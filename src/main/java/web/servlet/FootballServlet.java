package web.servlet;

import entity.db.Championship;
import entity.db.Season;
import org.apache.log4j.Logger;
import service.OverviewService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FootballServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(FootballServlet.class);

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

        try {
            List<Season> seasons = overviewService.findSeasons();
            List<Championship> championships = overviewService.findChampionships();

            req.setAttribute("seasons", seasons);
            req.setAttribute("championships", championships);

            req.getRequestDispatcher("footballjsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
