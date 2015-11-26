package service;

import entity.client.RoleData;
import entity.db.Permission;

import java.sql.SQLException;
import java.util.List;

public interface PermissionService {

    List<Permission> getPermissions() throws SQLException;

    List<Permission> getPermissions(int idRole) throws SQLException;

    int addPermissionToRole(int idPermission, int idRole) throws SQLException;

    int removePermissionFromRole(int idPermission, int idRole) throws SQLException;

    List<RoleData> getRoles() throws SQLException;

}
