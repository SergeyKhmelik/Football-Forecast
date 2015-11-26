package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class TransactionManager {

	private static final Logger LOGGER = Logger
			.getLogger(TransactionManager.class);

	public static final int FOOTBALL_DB = 1;
	public static final int USERS_DB = 2;

	private DataSource usersDs;
	private DataSource footballDs;

	public TransactionManager() throws NamingException {
		InitialContext initContext = new InitialContext();
		usersDs = (DataSource) initContext.lookup("java:comp/env/jdbc/users");
		footballDs = (DataSource) initContext.lookup("java:comp/env/jdbc/football");
	}

	public <T> T doTransaction(int database, Operation<T> operation) throws SQLException {
		T result = null;
		Connection connection = null;
		try {
			if(database == FOOTBALL_DB) {
				connection = footballDs.getConnection();
			} else if (database == USERS_DB){
				connection = usersDs.getConnection();
			}
			LOGGER.trace("Connection is taken from the pool.");
			if (connection == null) {
				throw new SQLException();
			}
			result = operation.execute(connection);
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					LOGGER.error("Connection rollback caused by SQLException.",
							e);
					connection.rollback();
				}
			} catch (SQLException e1) {
				LOGGER.error("Cannot rollback the connection.", e1);
				throw e1;
			}
			throw e;
		} finally {
			try {
				if (connection != null) {
					LOGGER.trace("Connection returned to the pool.");
					connection.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Cannot close the connection.", e);
				throw e;
			}
		}
		return result;
	}

}
