package stomp;

import java.security.Principal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import objects.Message;
import objects.MessageForm;
import objects.Notification;
@Service
public class MessageFeedServiceImpl implements MessageFeedService {

	private SimpMessageSendingOperations messaging;
	private Pattern pattern = Pattern.compile("\\@(\\S+)");
	private static final Logger logger = LoggerFactory.getLogger(MessageFeedServiceImpl.class);
	
	
	@Autowired
	public MessageFeedServiceImpl(SimpMessageSendingOperations messaging) {
		this.messaging = messaging;
	}
	
	@Override
	public void broadcastMessage(Message message) {
		this.messaging.convertAndSend("/topic/messagefeed",message);
		logger.info("Message "+message.getMessage()+" converted and sended.");
		Matcher matcher = this.pattern.matcher(message.getMessage());
		if(matcher.find()){
			String username = matcher.group(1);
			System.err.println("11111111METHOD1111111");
			this.messaging.convertAndSendToUser(
					username,
					"/queue/notifications",
					new Notification("You just got mentioned!"));
			logger.info("Message converted and notification sended to user with username "+username);
		}
	}

	
	

}
