package service;

import entity.client.UserInfo;
import entity.db.User;
import exceptions.NoSuchRoleException;
import exceptions.NoSuchUserException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

	String testUserService() throws SQLException;

	UserInfo logIn(String login, String password) throws SQLException, NoSuchUserException, NoSuchRoleException;

    int register(User user) throws SQLException;

	boolean validateOnDuplicateEmail(String email) throws SQLException;

	boolean validateOnDuplicateLogin(String login) throws SQLException;

	List<User> getUsers() throws SQLException;

	int blockUser(int idUser) throws SQLException;

}
