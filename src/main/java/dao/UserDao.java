package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entity.db.Permission;
import entity.db.Role;
import entity.db.User;

public interface UserDao {

	int insertUser(Connection conn, User user) throws SQLException;
	
	User getUser(Connection conn, String login, String password) throws SQLException;

	List<User> findUsers(Connection conn) throws SQLException;

	Role findRole(Connection conn, int idRole) throws SQLException;

	List<Permission> findPermissions(Connection conn, int idRole) throws SQLException;

	Boolean validateEmailOnDuplicateInsert(Connection conn, String email) throws SQLException;

	Boolean validateLoginOnDuplicateInsert(Connection conn, String login) throws SQLException;

	int blockUser(Connection conn, int idUser) throws SQLException;

}
