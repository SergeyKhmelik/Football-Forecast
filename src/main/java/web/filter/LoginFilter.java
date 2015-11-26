package web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String LOGIN_PAGE = "login";
    private static final String REGISTRATION_PAGE = "registration";
    private static final String CSS_JPG_PNG_GIF_JS = ".*(css|jpg|png|gif|js|jspf)";
    private static final String USER = "user";
    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);

    private String excludePatterns;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        this.excludePatterns = cfg.getInitParameter("excludePatterns");
        LOGGER.debug("LoginFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (validUrl(req)) {
            chain.doFilter(request, response);
        } else {
            LOGGER.debug("No user logged in " + req.getRequestURI());
            resp.sendRedirect(LOGIN_PAGE);
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("LoginFilter instance destroyed.");
    }

    private boolean validUrl(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String requestURL = req.getRequestURL().toString();
        return matchesURL(requestURL) || session.getAttribute(USER) != null;
    }

    private boolean matchesURL(String requestURL) {
        return (matchesExcludePatterns(requestURL) ||
                matchesUploadingComponents(requestURL) ||
                matchesErrorPage(requestURL) ||
                matchesRegistrationPage(requestURL));
    }

    private boolean matchesExcludePatterns(String requestURL) {
        return requestURL.contains(excludePatterns);
    }

    private boolean matchesUploadingComponents(String requestURL) {
        return requestURL.matches(CSS_JPG_PNG_GIF_JS);
    }

    private boolean matchesErrorPage(String requestURL){
        return requestURL.contains("error");
    }

    private boolean matchesRegistrationPage(String requestURL) {
        return requestURL.contains(REGISTRATION_PAGE);
    }

}