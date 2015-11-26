package web.servlet;

import entity.db.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UsersServlet extends HttpServlet {

    public static final Logger LOGGER = Logger.getLogger(UsersServlet.class);

    public UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            LOGGER.error("Could not get services from application context");
            throw new UnavailableException(
                    "Could not get user service.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        LOGGER.info("Entered usermanagement servlet");

        try {
            List<User> users = userService.getUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("usersjsp").forward(req, resp);

        } catch (SQLException ex) {
            LOGGER.error("Exception during user reading.");
            resp.sendRedirect("error");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            if ("block".equals(action)) {
                int idUser = Integer.parseInt(req.getParameter("idUser"));
                userService.blockUser(idUser);
                resp.setContentType("text/plain");
                resp.getWriter().write("User has been successfully blocked/unblocked.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
