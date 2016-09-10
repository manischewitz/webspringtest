package rmi;

import java.util.List;

import objects.Message;
import objects.User;

public interface ApplicationService {
	List<Message> findMessages(long max, int count);
	Message save(Message message);
	User save(User user);
	User findOne(long id);
	List<Message> getMessagesForUser(String username);
	User getUser(String username);
	Message findOneMessage(long id);
	boolean delete(long id);
	List<User> findAll();

}
