package web.servlet;

import entity.db.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);

    // EPL Patterns
    private static final String NAME_PATTERN = "^[\\p{L}{2,}...]+$";
    private static final String LOGIN_PATTERN = "^[a-z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{4,20}$";
    private static final String EMAIL_PATTERN = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";

    //Error massage names
    private static final String NAMING_PATTERN_ERROR = "namingPatternError";
    private static final String LOGIN_PATTERN_ERROR = "loginPatternError";
    private static final String PASSWORD_PATTERN_ERROR = "passwordPatternError";
    private static final String EMAIL_PATTERN_ERROR = "emailPatternError";
    private static final String EMPTY_FIELDS_ERROR = "emptyFieldsError";
    private static final String LOGIN_IN_USE_ERROR = "loginInUseError";
    private static final String EMAIL_IN_USE_ERROR = "emailInUseError";
    private static final String PASSWORD_CONFIRMATION_ERROR = "passwordConfirmationError";

    // ERROR MESSAGES
    private static final String NAMING_PATTERN_ERROR_MESSAGE = "User name/surname should contain 2 or more symbols.";
    private static final String LOGIN_PATTERN_ERROR_MESSAGE = "User login should be 3-15 symbols without  or more symbols.";
    private static final String PASSWORD_PATTERN_ERROR_MESSAGE = "Password should contain only letters or numbers. It should contain more than 3 and less then 20 symbols long.";
    private static final String EMAIL_PATTERN_ERROR_MESSAGE = "Email should contain 2 or more symbols.";
    private static final String EMPTY_FIELDS_ERROR_MESSAGE = "Please fill in all fields.";
    private static final String LOGIN_IN_USE_ERROR_MESSAGE = "This login is already in use. Please try another one.";
    private static final String EMAIL_IN_USE_ERROR_MESSAGE = "This email is already in use. Please try another one";
    private static final String PASSWORD_CONFIRMATION_ERROR_MESSAGE = "Passwords are not equal.";


    private UserService userService;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");

        HttpSession session = req.getSession();

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        System.out.println("MOU EMAIL " + email + "  sadsad  " + user.getEmail());

        if (validateEmptyFields(user, session) &&
                validateConfirmedPasswordEquality(password, confirmedPassword, session)) {
            try {
                if (validateUser(user, session)) {
                    userService.register(user);
                    session.removeAttribute("registration");
                    resp.sendRedirect("login");
                } else {
                    LOGGER.info("Validation failed.");
                    session.setAttribute("registration", "error");
                    resp.sendRedirect("login");
                    //req.getRequestDispatcher("login").forward(req, resp);
                }
            } catch (SQLException e) {
                LOGGER.error("SQLException during users registration.");
                resp.sendRedirect("error");
            }
        } else {
            session.setAttribute("registration", "error");
            resp.sendRedirect("login");
        }
    }

    private boolean validateUser(User user, HttpSession session) throws SQLException {
        return validateUserOnPatterns(user, session) &
                validateUserOnDuplicateInsert(user, session);
    }

    private boolean validateEmptyFields(User user, HttpSession session) {
        if (user.getName() != null &&
                !user.getName().equals("") &&
                user.getLogin() != null &&
                !user.getLogin().equals("") &&
                user.getSurname() != null &&
                !user.getSurname().equals("") &&
                user.getPassword() != null &&
                !user.getPassword().equals("") &&
                user.getEmail() != null &&
                !user.getEmail().equals("")) {
            return true;
        } else {
            LOGGER.info("Empty fields validation failed.");
            session.setAttribute(EMPTY_FIELDS_ERROR, EMPTY_FIELDS_ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validateUserOnPatterns(User user, HttpSession session) {
        return validateName(user.getName(), session) &
                validateName(user.getSurname(), session) &
                validateLogin(user.getLogin(), session) &
                validatePassword(user.getPassword(), session) &
                validateEmail(user.getEmail(), session);
    }

    private boolean validateUserOnDuplicateInsert(User user, HttpSession session) throws SQLException {
        return validateOnDuplicateLogin(user.getLogin(), session) &
                validateOnDuplicateEmail(user.getEmail(), session);
    }

    private boolean validateOnDuplicateEmail(String email, HttpSession session) throws SQLException {
        if (!userService.validateOnDuplicateEmail(email)) {
            LOGGER.info("Duplicate email validation failed.");
            session.setAttribute(EMAIL_IN_USE_ERROR, EMAIL_IN_USE_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateOnDuplicateLogin(String login, HttpSession session) throws SQLException {
        if (!userService.validateOnDuplicateLogin(login)) {
            LOGGER.info("Duplicate login validation failed.");
            session.setAttribute(LOGIN_IN_USE_ERROR, LOGIN_IN_USE_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateName(String userName, HttpSession session) {
        if (!userName.matches(NAME_PATTERN)) {
            LOGGER.info("Name validation failed.");
            session.setAttribute(NAMING_PATTERN_ERROR, NAMING_PATTERN_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String userEmail, HttpSession session) {
        if (!userEmail.matches(EMAIL_PATTERN)) {
            LOGGER.info("Email validation failed.");
            session.setAttribute(EMAIL_PATTERN_ERROR, EMAIL_PATTERN_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateLogin(String userLogin, HttpSession session) {
        if (!userLogin.matches(LOGIN_PATTERN)) {
            LOGGER.info("Login validation failed.");
            session.setAttribute(LOGIN_PATTERN_ERROR, LOGIN_PATTERN_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validatePassword(String userPassword, HttpSession session) {
        if (!userPassword.matches(PASSWORD_PATTERN)) {
            LOGGER.info("Password validation failed.");
            session.setAttribute(PASSWORD_PATTERN_ERROR, PASSWORD_PATTERN_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateConfirmedPasswordEquality(String userPassword, String confirmedPassword, HttpSession session) {
        if (!userPassword.equals(confirmedPassword)) {
            LOGGER.info("Confirmed password validation failed.");
            session.setAttribute(PASSWORD_CONFIRMATION_ERROR, PASSWORD_CONFIRMATION_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
