package jaxws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import db.MessagesRepository;
import objects.Message;
import objects.User;
@Component
@WebService(endpointInterface = "jaxws.ApplicationService",serviceName="applicationService")
public class ApplicationServiceEndpoint implements ApplicationService{

	@Autowired
	MessagesRepository messageRepository;
	
	
	public List<Message> findMessages(long max, int count) {
		return this.findMessages(max, count);
	}

	public Message saveMessage(Message message) {
		System.err.println("User was saved.");
		return new Message();
	}

	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Message> getMessagesForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message findOneMessage(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
