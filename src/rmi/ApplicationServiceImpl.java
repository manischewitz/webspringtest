package rmi;

import java.util.List;

import org.springframework.stereotype.Component;

import objects.Message;
import objects.User;
@Component
public class ApplicationServiceImpl implements ApplicationService {

	@Override
	public List<Message> findMessages(long max, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message save(Message message) {
		System.err.println("User was saved.");
		return new Message();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMessagesForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findOneMessage(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
