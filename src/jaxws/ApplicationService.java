package jaxws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import objects.Message;
import objects.User;
@WebService(serviceName="applicationService")
public interface ApplicationService {
	//methods should have unique names
	@WebMethod
	List<Message> findMessages(long max, int count);
	@WebMethod
	Message saveMessage(Message message);
	@WebMethod
	User saveUser(User user);
	@WebMethod
	User findOne(long id);
	@WebMethod
	List<Message> getMessagesForUser(String username);
	@WebMethod
	User getUser(String username);
	@WebMethod
	Message findOneMessage(long id);
	@WebMethod
	boolean delete(long id);
	@WebMethod
	List<User> findAll();

}
