package web.servlet;

import entity.client.Prediction;
import org.apache.log4j.Logger;
import service.ForecastService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ForecastServet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ForecastServet.class);
    private ForecastService forecastService;

    @Override
    public void init() throws ServletException {
        forecastService = (ForecastService) getServletContext().getAttribute("forecastService");
        if (forecastService == null) {
            LOGGER.error("Could not get services from application context");
            throw new UnavailableException(
                    "Could not get user service.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        int idMatch = Integer.parseInt(req.getParameter("idMatch"));
        try {
            Prediction prediction = forecastService.makePrediction(idMatch);
            req.setAttribute("prediction", prediction);
            req.getRequestDispatcher("forecastjsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
