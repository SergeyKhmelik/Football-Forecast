package dao;

import entity.db.Permission;
import entity.db.Role;
import entity.db.RolePermission;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PermissionDao {
    List<Permission> readPermissions(Connection conn)
            throws SQLException;

    List<Permission> readPermissions(Connection conn, int idRole)
            throws SQLException;

    List<Permission> readMissingPermissions(Connection conn,
                                            int idRole) throws SQLException;

    int createRolePermission(Connection conn,
                             RolePermission rolePermission) throws SQLException;

    int deleteRolePermission(Connection conn,
                             RolePermission rolePermission) throws SQLException;

    Role readRole(Connection conn, String roleName) throws SQLException;

    Role readRole(Connection conn, int idRole) throws SQLException;

    List<Role> readRoles(Connection conn) throws SQLException;
}
