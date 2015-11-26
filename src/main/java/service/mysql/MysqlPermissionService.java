package service.mysql;

import dao.PermissionDao;
import entity.client.RoleData;
import entity.db.Permission;
import entity.db.Role;
import entity.db.RolePermission;
import org.apache.log4j.Logger;
import service.PermissionService;
import util.Operation;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlPermissionService implements PermissionService {


    private static final Logger LOGGER = Logger
            .getLogger(MysqlPermissionService.class);

    private TransactionManager transactionManager;
    private PermissionDao permissionDao;

    public MysqlPermissionService(TransactionManager transactionManager,
                                  PermissionDao permissionDao) {
        this.transactionManager = transactionManager;
        this.permissionDao = permissionDao;
    }

    @Override
    public List<Permission> getPermissions() throws SQLException {
        return transactionManager
                .doTransaction(TransactionManager.USERS_DB, new Operation<List<Permission>>() {

                    @Override
                    public List<Permission> execute(Connection conn)
                            throws SQLException {
                        return permissionDao.readPermissions(conn);
                    }
                });
    }

    @Override
    public List<Permission> getPermissions(final int idRole) throws SQLException {
        return transactionManager
                .doTransaction(TransactionManager.USERS_DB, new Operation<List<Permission>>() {

                    @Override
                    public List<Permission> execute(Connection conn)
                            throws SQLException {
                        return permissionDao.readPermissions(conn, idRole);
                    }
                });
    }

    @Override
    public int addPermissionToRole(int idPermission, int idRole) throws SQLException {
        final RolePermission rolePermission;
        rolePermission = new RolePermission();
        rolePermission.setIdRole(idRole);
        rolePermission.setIdPermission(idPermission);

        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return permissionDao.createRolePermission(conn, rolePermission);
            }
        });
    }

    @Override
    public int removePermissionFromRole(int idPermission, int idRole) throws SQLException {
        final RolePermission rolePermission;
        rolePermission = new RolePermission();
        rolePermission.setIdRole(idRole);
        rolePermission.setIdPermission(idPermission);

        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return permissionDao.deleteRolePermission(conn, rolePermission);
            }
        });
    }

    @Override
    public List<RoleData> getRoles() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<List<RoleData>>(){

            @Override
            public List<RoleData> execute(Connection conn)
                    throws SQLException {
                List<RoleData> result = new ArrayList<RoleData>();

                List<Role> roles = permissionDao.readRoles(conn);
                for(Role role: roles){
                    RoleData roleData = new RoleData();
                    roleData.setIdRole(role.getIdRole());
                    roleData.setName(role.getName());
                    roleData.setDescription(role.getDescription());
                    roleData.setPermissions(permissionDao.readPermissions(conn, role.getIdRole()));
                    roleData.setMissingPermissions(permissionDao.readMissingPermissions(conn,role.getIdRole()));
                    result.add(roleData);
                }
                return result;
            }
        });
    }

}
