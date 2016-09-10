package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import objects.Message;
import web.AuthorityManager;

@Profile("jdbc")
@Component
public class MessageRepoImpl implements MessagesRepository {

	public final static String TABLE_NAME = "messages";
	private final static String SQL_SAVE_MESSAGE = "INSERT INTO "+TABLE_NAME+" (posted_by,message,latitude,longitude) VALUES(?,?,?,?)"; 
	private final static String SQL_FIND_MESSAGES = "SELECT id,posted_by,message,post_date,latitude,longitude FROM "+TABLE_NAME+
			" WHERE id<? ORDER BY id DESC LIMIT ?;";
	private final static String SQL_FIND_BY_ID = "SELECT id,posted_by,message,post_date,latitude,longitude FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_DELETE_MESSAGE = "DELETE FROM "+TABLE_NAME+" WHERE id=?;";
	private final static String SQL_FIND_MESSAGES_FOR_USER ="SELECT id,posted_by,message,post_date,latitude,longitude FROM "+TABLE_NAME+" WHERE posted_by = ?;";
	private final static String SQL_UPDATE_MESSAGE = "UPDATE "+TABLE_NAME+" SET message=?,posted_by=? WHERE id=?;";
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<Message> findLastMessages(long max, int count) {
		List<Message> messagesList = new ArrayList<Message>(count);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_MESSAGES);
			preparedStatement.setLong(1, max);
			preparedStatement.setInt(2, count);
			resultSet = preparedStatement.executeQuery();
			Message message = null;
			
			for(int i =0;i<count ; i++){
				if(resultSet.next()){
				message = new Message(resultSet.getString("message"), resultSet.getTimestamp("post_date"), resultSet.getDouble("latitude"),
						resultSet.getDouble("longitude"), resultSet.getLong("id"),resultSet.getString("posted_by"));
				 messagesList.add(message);
				}	
			}
			return messagesList;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public Message findOne(long id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			Message message = null;
			
			if(resultSet.next()){
				message = new Message(resultSet.getString("message"), resultSet.getTimestamp("post_date"), resultSet.getDouble("latitude"),
						resultSet.getDouble("longitude"), id,resultSet.getString("posted_by"));
			}
			return message;
			
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public Message save(Message message) {
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		/*CREATE TABLE IF NOT EXISTS messages (
		id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
		message VARCHAR(4096) NOT NULL,
		post_date timestamp DEFAULT CURRENT_TIMESTAMP,
        latitude FLOAT,
		longitude FLOAT);*/
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SAVE_MESSAGE,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, message.getUsername());
			preparedStatement.setString(2, message.getMessage());
			preparedStatement.setDouble(3, message.getLatitude());
			preparedStatement.setDouble(4, message.getLongitude());
			preparedStatement.execute();
			 resultSet = preparedStatement.getGeneratedKeys();
			if(resultSet.next())  message.setId(resultSet.getLong(1));
			return message;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return null;
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
	}

	public boolean delete(long id){
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_DELETE_MESSAGE);
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
		//add feature: write deleted message into another table for deleted messages
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

	@Override
	public List<Message> getMessagesForUser(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FIND_MESSAGES_FOR_USER);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			List<Message> messagesList = new ArrayList<>(100);;
			while(resultSet.next()){
				messagesList.add(new Message());
			}
			return messagesList;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, resultSet);
		}
		return null;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Message updateMessage(Message updatedMessage) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = this.dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_UPDATE_MESSAGE);
			preparedStatement.setString(1, updatedMessage.getMessage());
			preparedStatement.setString(2, updatedMessage.getUsername());
			preparedStatement.setLong(3, updatedMessage.getId());
			preparedStatement.executeUpdate();
			return updatedMessage;
		}catch(SQLException sqle){
			System.err.println("SQLException");
			sqle.printStackTrace();
		}finally{
			this.catchExceptions(connection, preparedStatement, null);
		}
		return null;
	}
}
