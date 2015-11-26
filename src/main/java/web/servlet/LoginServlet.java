package web.servlet;

import entity.client.UserInfo;
import entity.db.Permission;
import entity.db.User;
import exceptions.NoSuchRoleException;
import exceptions.NoSuchUserException;
import org.apache.log4j.Logger;
import service.PermissionService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    private static final String LOGPASS_VALIDATION_ERROR_MESSAGE = "Login or password cannot be empty.";

    private UserService userService;
    private PermissionService permissionService;


    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        permissionService = (PermissionService) getServletContext().getAttribute("permissionService");
        if (userService == null || permissionService == null) {
            LOGGER.error("Could not get services from application context");
            throw new UnavailableException(
                    "Could not get user service.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        LOGGER.info("Entered login servlet: login=" + login + " password="
                + password);

        UserInfo user;
        List<Permission> permissions;

        HttpSession session = request.getSession();

        if (!validate(login, password)) {
            session.setAttribute("loginvalidation",
                    LOGPASS_VALIDATION_ERROR_MESSAGE);
            response.sendRedirect("login");
            return;
        }

        try {
            user = userService.logIn(login, password);
            permissions = permissionService.getPermissions(user.getRole().getIdRole());


            session.setAttribute("user", user);
            session.setAttribute("permissions", permissions);

            LOGGER.info("User " + user.getLogin() + "(id:" + user.getIdUser()
                    + ") logged in. ");

            response.sendRedirect("football");
        } catch (NoSuchUserException | NoSuchRoleException e) {
            LOGGER.error("NoSuchUser or NoSuchRole exception during users authorization.");
            session.setAttribute("loginerror", e.getMessage());
            response.sendRedirect("login");
            return;
        } catch (SQLException e) {
            LOGGER.error("SQLException during users authorization.");
            response.sendRedirect("error");
            return;
        }
    }

    private boolean validate(String login, String password) {
        if (login == null || password == null || login == "" || password == "") {
            return false;
        }
        return true;
    }

}
