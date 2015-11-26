package web.servlet;

import entity.client.RoleData;
import org.apache.log4j.Logger;
import service.PermissionService;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PermissionServlet.class);

    private PermissionService permissionService;

    @Override
    public void init() throws ServletException {
        permissionService = (PermissionService) getServletContext().getAttribute("permissionService");

        if(permissionService == null){
            throw new UnavailableException("Couldn`t get DAO");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        LOGGER.info("Entered permissionManagement servlet");

        List<RoleData> roles = new ArrayList<RoleData>();

        try {
            roles = permissionService.getRoles();
        } catch (SQLException e) {
            LOGGER.error("Exception during role reading.");
            response.sendRedirect("error");
            return;
        }

        request.setAttribute("roles", roles);

        request.getRequestDispatcher("permissionsjsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "delete":
                    deletePermission(req, resp);
                    break;
                case "add":
                    addPermission(req, resp);
                    break;
                default:
            }
        } catch (SQLException e) {
            //TODO exception add
            e.printStackTrace();
        }
    }

    private void addPermission(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int idPermission = Integer.parseInt(req.getParameter("idPermission"));
        int idRole = Integer.parseInt(req.getParameter("idRole"));
        permissionService.addPermissionToRole(idPermission, idRole);
        resp.sendRedirect("permissions");
    }

    private void deletePermission(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int idPermission = Integer.parseInt(req.getParameter("idPermission"));
        int idRole = Integer.parseInt(req.getParameter("idRole"));
        permissionService.removePermissionFromRole(idPermission, idRole);
        resp.sendRedirect("permissions");
    }
}
