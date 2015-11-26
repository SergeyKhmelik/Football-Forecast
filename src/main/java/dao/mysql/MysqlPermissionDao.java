package dao.mysql;

import dao.PermissionDao;
import entity.db.Permission;
import entity.db.Role;
import entity.db.RolePermission;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlPermissionDao implements PermissionDao {

    private final static Logger LOGGER = Logger
            .getLogger(MysqlPermissionDao.class);

    private static final String FIND_ROLES = "SELECT * FROM role";
    private static final String FIND_PERMISSIONS = "SELECT * FROM permission";
    private static final String FIND_PERMISSIONS_BY_ROLEID = "SELECT * FROM permission WHERE idpermission IN (SELECT idpermission FROM role_permission WHERE idrole=?)";
    private static final String ADD_PERMISSION_TO_ROLE = "INSERT INTO role_permission VALUES (?, ?)";
    private static final String REMOVE_PERMISSION_FROM_ROLE = "DELETE FROM role_permission WHERE idrole=? AND idpermission=?";
    private static final String FIND_ROLE_BY_NAME = "SELECT * FROM role WHERE name=?";
    private static final String FIND_ROLE_BY_ID = "SELECT * FROM role WHERE idrole=?";
    private static final String FIND_MISSING_ROLE_PERMISSIONS = "SELECT * FROM permission WHERE idpermission NOT IN (SELECT idpermission FROM role_permission WHERE idrole=?)";

    @Override
    public List<Permission> readPermissions(Connection conn)
            throws SQLException {
        List<Permission> result = new ArrayList<Permission>();
        try (Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery(FIND_PERMISSIONS);
            Permission currentPermission;
            while (rs.next()) {
                currentPermission = new Permission();
                currentPermission.setIdPermission(rs.getInt(1));
                currentPermission.setName(rs.getString(2));
                currentPermission.setDescription(rs.getString(3));
                result.add(currentPermission);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot read permission entities ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Permission> readPermissions(Connection conn, int idRole)
            throws SQLException {
        List<Permission> result = new ArrayList<Permission>();
        Permission currentPermission;
        try (PreparedStatement pstm = conn
                .prepareStatement(FIND_PERMISSIONS_BY_ROLEID)) {
            pstm.setInt(1, idRole);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                currentPermission = new Permission();
                currentPermission.setIdPermission(rs.getInt(1));
                currentPermission.setName(rs.getString(2));
                currentPermission.setDescription(rs.getString(3));
                result.add(currentPermission);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot read permission entities where idRole="
                    + idRole, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Permission> readMissingPermissions(Connection conn,
                                                   int idRole) throws SQLException {
        List<Permission> result = new ArrayList<Permission>();
        Permission currentPermission;
        try (PreparedStatement pstm = conn
                .prepareStatement(FIND_MISSING_ROLE_PERMISSIONS)) {
            pstm.setInt(1, idRole);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                currentPermission = new Permission();
                currentPermission.setIdPermission(rs.getInt(1));
                currentPermission.setName(rs.getString(2));
                currentPermission.setDescription(rs.getString(3));
                result.add(currentPermission);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot read permission entities where idRole="
                    + idRole, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public int createRolePermission(Connection conn,
        RolePermission rolePermission) throws SQLException {
        try (PreparedStatement pstm = conn
                .prepareStatement(ADD_PERMISSION_TO_ROLE)) {
            pstm.setInt(1, rolePermission.getIdRole());
            pstm.setInt(2, rolePermission.getIdPermission());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot create a role_permission ", ex);
            throw ex;
        }
        return rolePermission.getIdPermission();
    }

    @Override
    public int deleteRolePermission(Connection conn,
                                    RolePermission rolePermission) throws SQLException {
        try (PreparedStatement pstm = conn
                .prepareStatement(REMOVE_PERMISSION_FROM_ROLE)) {
            pstm.setInt(1, rolePermission.getIdRole());
            pstm.setInt(2, rolePermission.getIdPermission());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot delete role_permission with idRole="
                    + rolePermission.getIdRole() + " and idPermission="
                    + rolePermission.getIdPermission(), ex);
            throw ex;
        }
        return rolePermission.getIdPermission();
    }

    @Override
    public Role readRole(Connection conn, String roleName) throws SQLException {
        Role result = null;
        try (PreparedStatement pstm = conn.prepareStatement(FIND_ROLE_BY_NAME)) {
            pstm.setString(1, roleName);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Role();
                result.setIdRole(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setDescription(rs.getString(3));
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot read role with name " + roleName, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public Role readRole(Connection conn, int idRole) throws SQLException {
        Role result = null;
        try (PreparedStatement pstm = conn.prepareStatement(FIND_ROLE_BY_ID)) {
            pstm.setInt(1, idRole);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Role();
                result.setIdRole(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setDescription(rs.getString(3));
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot read role with id " + idRole, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Role> readRoles(Connection conn) throws SQLException {
        List<Role> result = new ArrayList<Role>();
        try(Statement stm = conn.createStatement()){
            ResultSet rs = stm.executeQuery(FIND_ROLES);
            while(rs.next()){
                Role currentRole = new Role();
                currentRole.setIdRole(rs.getInt(1));
                currentRole.setName(rs.getString(2));
                currentRole.setDescription(rs.getString(3));
                result.add(currentRole);
            }
        } catch (SQLException ex){
            LOGGER.error("Cannot read all roles", ex);
            throw ex;
        }
        return result;
    }

}
