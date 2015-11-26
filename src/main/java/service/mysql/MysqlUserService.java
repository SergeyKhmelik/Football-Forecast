package service.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entity.client.UserInfo;
import entity.db.User;
import exceptions.NoSuchRoleException;
import exceptions.NoSuchUserException;
import org.apache.log4j.Logger;

import dao.UserDao;
import service.UserService;
import util.Operation;
import util.TransactionManager;

public class MysqlUserService implements UserService {

    private static final Logger LOGGER = Logger
            .getLogger(MysqlUserService.class);

    private TransactionManager transactionManager;
    private UserDao userDao;

    public MysqlUserService(TransactionManager transactionManager,
                            UserDao userDao) {
        this.transactionManager = transactionManager;
        this.userDao = userDao;
    }

    @Override
    public String testUserService() throws SQLException {
        String result = transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<String>() {

            @Override
            public String execute(Connection conn) throws SQLException {
                return userDao.getUser(conn, "admin", "admin").toString();
            }
        });
        return result;
    }

    @Override
    public UserInfo logIn(final String login, final String password) throws SQLException, NoSuchUserException, NoSuchRoleException {
        UserInfo user =  transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<UserInfo>() {

            @Override
            public UserInfo execute(Connection conn) throws SQLException {
                User user = userDao.getUser(conn, login, password);
                UserInfo result = null;
                if(user != null){
                    result = new UserInfo(user);
                    result.setRole(userDao.findRole(conn, user.getIdRole()));
                    result.setPermissions(userDao.findPermissions(conn, user.getIdRole()));
                 }
                return result;
            }

        });

        if (user == null) {
            throw new NoSuchUserException();
        }

        if (user.getRole() == null) {
            throw new NoSuchRoleException();
        }
        return user;
    }

    @Override
    public int register(final User user) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Integer>() {

            @Override
            public Integer execute(Connection conn) throws SQLException {
                return userDao.insertUser(conn, user);
            }

        });
    }

    @Override
    public boolean validateOnDuplicateEmail(final String email) throws SQLException{
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Boolean>() {

            @Override
            public Boolean execute(Connection conn) throws SQLException {
                return userDao.validateEmailOnDuplicateInsert(conn, email);
            }
        });

    }

    @Override
    public boolean validateOnDuplicateLogin(final String login) throws SQLException{
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Boolean>() {

            @Override
            public Boolean execute(Connection conn) throws SQLException {
                return userDao.validateLoginOnDuplicateInsert(conn, login);
            }
        });

    }

    @Override
    public List<User> getUsers() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<List<User>>() {
            @Override
            public List<User> execute(Connection conn) throws SQLException {
                return userDao.findUsers(conn);
            }
        });
    }

    @Override
    public int blockUser(final int idUser) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.USERS_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return userDao.blockUser(conn, idUser);
            }
        });
    }

}
