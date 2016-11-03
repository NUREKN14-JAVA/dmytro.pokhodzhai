package com.dmitrypokhodzhay.usermanagement.db;

import com.dmitrypokhodzhay.usermanagement.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.sql.Date;

class HsqldbUserDao implements UserDao {

	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth, age FROM users";

	private static final String INSERT_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth, age) VALUES (?, ?, ?)";

	private static final String FIND_QUERY = "SELECT id, firstname, lastname, dateofbirth, age FROM users WHERE id=?";

	private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=?, age=? WHERE id=?";

	private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
	private static final String FIND_BY_NAMES_QUERY = "SELECT id, firstname, lastname, dateofbirth, age FROM users WHERE firstname=? AND lastname=?";

	private ConnectionFactory connectionFactory;

	private int lastOperationCounter;

	public HsqldbUserDao() {
		lastOperationCounter = 0;
	}

	public HsqldbUserDao(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public User create(User user) throws DatabaseException {

		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, (Date) user.getDateOfBirthd());
			statement.setInt(4, user.getAge());

			lastOperationCounter = statement.executeUpdate();

			CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
			
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next())
				user.setId(new Long(keys.getLong(1)));
			keys.close();
			
			callableStatement.close();
			statement.close();
			connection.close();

			return user;
			
		} catch (DatabaseException e) {
			throw (e);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public void update(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
			
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, (Date) user.getDateOfBirthd());
			statement.setInt(4, user.getAge());
			statement.setLong(4, user.getId().longValue());
			
			lastOperationCounter = statement.executeUpdate();
			
			statement.close();
			connection.close();
			
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public void delete(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
			statement.setLong(1, user.getId().longValue());
			lastOperationCounter = statement.executeUpdate();
			
			statement.close();
			connection.close();
			
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public User find(Long id) throws DatabaseException {
		User result = null;
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
			statement.setLong(1, id.longValue());
			ResultSet resultSet = statement.executeQuery();
			
			if (!resultSet.next()) {
				throw new DatabaseException("Could not find the user with id=" + id);
			}
			
			result = new User();
			result.setId(new Long(resultSet.getLong(1)));
			result.setFirstName(resultSet.getString(2));
			result.setLastName(resultSet.getString(3));
			
			Calendar temp = Calendar.getInstance();
			temp.setTime(result.getDateOfBirthd());
			result.setDateOfBirthd(temp.getTime());
			
			resultSet.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
	}

	public Collection findAll() throws DatabaseException {
		Collection result = new LinkedList();
		try {
			Connection connection = connectionFactory.createConnection();

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				
				Calendar temp = Calendar.getInstance();
				temp.setTime(resultSet.getDate(4));
				user.setDateOfBirthd(temp.getTime());
				
				result.add(user);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
	}

	public Collection find(String firstName, String lastName) throws DatabaseException {
		Collection result = new LinkedList();
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(FIND_BY_NAMES_QUERY);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				
				Calendar temp = Calendar.getInstance();
				temp.setTime(resultSet.getDate(4));
				user.setDateOfBirthd(temp.getTime());
				
				result.add(user);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
	}

}
