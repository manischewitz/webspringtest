package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import objects.User;
import web.AuthorityManager;
@Profile("jdbc")
@Component
public class UserRepoImpl implements UserRepository {

	private final static String TABLE_NAME = "newUsers";
	private final static String SQL_INSERT_USER = "INSERT INTO "+TABLE_NAME+
			" (username,password,email,role,registration_date,last_name,first_name) VALUES(?,?,?,?,?,?,?);";
	private final static String SQL_UPDATE_USER = "UPDATE "+TABLE_NAME+" SET username=?,password=?,email=? where id=?;";
	private final static String SQL_SELECT_USER = "SELECT id,username,password,email,role,registration_date,last_name,first_name FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_SELECT_BY_USERNAME = "SELECT id,username,password,email,"
			+ "role,registration_date,last_name,first_name FROM "+TABLE_NAME+" WHERE username=?;";
	private final static String SQL_DELETE_USER = "DELETE FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_FIND_ALL_USERS ="SELECT * FROM "+TABLE_NAME+";";
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public User save(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		 java.util.Date today = new java.util.Date();
		 Date date = new java.sql.Date(today.getTime());
		
		/*CREATE TABLE IF NOT EXISTS newUsers (
		id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
		username VARCHAR(64) NOT NULL UNIQUE,
		password varchar(128) NOT NULL,
		email VARCHAR(64) NOT NULL UNIQUE,
		role   VARCHAR(32) NOT NULL DEFAULT 'user', 
		registration_date timestamp DEFAULT CURRENT_TIMESTAMP,
		last_name VARCHAR(32) , 
		first_name VARCHAR(32));*/
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, "user");
			preparedStatement.setDate(5, date);
			preparedStatement.setString(6, user.getLastName());
			preparedStatement.setString(7, user.getFirstName());
			preparedStatement.execute();
			resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next()) user.setId(resultSet.getLong(1));
			user.setRole("user");
			user.setRegistration_date(date);
			return user;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return user;
	}
	
	public User  update(User user){
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setLong(4, user.getId());
			preparedStatement.execute();
			return user;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, null);
		}
		return null;
	}
	
	public User findOne(long id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_USER);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			User user = null;
			
			if(resultSet.next()){
				user = new User();
				user.setId(resultSet.getLong("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setRegistration_date(resultSet.getDate("registration_date"));
				user.setRole(resultSet.getString("role"));
			}
			return user;
			
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
			return null;
	}

	@Override
	public User findByLogin(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USERNAME);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			User user = null;
			
			if(resultSet.next()){
				user = new User();
				user.setId(resultSet.getLong("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setRegistration_date(resultSet.getDate("registration_date"));
				user.setRole(resultSet.getString("role"));
			}
			return user;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	private void catchExceptions(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
		
		try{
			if(resultSet != null) resultSet.close(); 
			
			if(preparedStatement != null) preparedStatement.close();
			
			if(connection != null) connection.close();
			
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public long count(){
		return this.findAll().size();
	};

	@Override
	public List<User> findAll() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);
			resultSet = preparedStatement.executeQuery();
			List<User> users = new ArrayList<>(100);
			while(resultSet.next()){ 
				users.add(new User(
						resultSet.getLong("id"), 
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						resultSet.getString("role"),
						resultSet.getDate("registration_date")));
			}
			return users;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public boolean delete(Long id) {
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
			preparedStatement.setLong(1, id);
			preparedStatement.execute();
			return true;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement,null);
		}
		return false;
	}

	
	
}
