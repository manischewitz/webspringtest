package email;



import javax.mail.MessagingException;

import objects.Message;

public interface EmailService {

	public void sendMessage(String to,String from,Message message) throws MessagingException;
	
	public void sendSimpleTextMessage(String to,String from,Message message);
}
