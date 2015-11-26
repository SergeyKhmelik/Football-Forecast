package web.listener;

import dao.FootballEventDao;
import dao.ForecastDao;
import dao.PermissionDao;
import dao.UserDao;
import dao.mysql.MysqlFootballEventDao;
import dao.mysql.MysqlForecastDao;
import dao.mysql.MysqlPermissionDao;
import dao.mysql.MysqlUserDao;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import service.ForecastService;
import service.OverviewService;
import service.PermissionService;
import service.UserService;
import service.mysql.MysqlForecastService;
import service.mysql.MysqlOverviewService;
import service.mysql.MysqlPermissionService;
import service.mysql.MysqlUserService;
import util.TransactionManager;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    public static final Logger LOGGER = Logger
            .getLogger(AppContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        initLog4J(servletContext);
        TransactionManager transactionManager;
        try {
            transactionManager = new TransactionManager();
        } catch (NamingException e) {
            LOGGER.error("Cannot get connection from the pool.", e);
            throw new RuntimeException("NamingException", e);
        }

        //DAO creation here
        UserDao userDao = new MysqlUserDao();
        FootballEventDao footballEventDao = new MysqlFootballEventDao();
        PermissionDao permissionDao = new MysqlPermissionDao();
        ForecastDao forecastDao = new MysqlForecastDao();

        //Service creation here
        UserService userService = new MysqlUserService(transactionManager, userDao);
        OverviewService overviewService = new MysqlOverviewService(transactionManager, footballEventDao, userDao);
        PermissionService permissionService = new MysqlPermissionService(transactionManager, permissionDao);
        ForecastService forecastService = new MysqlForecastService(transactionManager, forecastDao, overviewService);

        //Put it all into context attributes
        servletContext.setAttribute("forecastService" ,forecastService);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("overviewService", overviewService);
        servletContext.setAttribute("permissionService", permissionService);

        LOGGER.info("App context initialized");
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext
                    .getRealPath("WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            LOGGER.error("Cannot configure Log4j", ex);
        }
        log("Log4J initialization finished");
        LOGGER.debug("Log4j has been initialized");
    }

    private void log(String msg) {
        System.out.println("[AppContextListener] " + msg);
    }

}