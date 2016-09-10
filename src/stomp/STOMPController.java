package stomp;

import java.security.Principal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import db.MessagesRepository;
import objects.Message;
import objects.MessageForm;
import objects.Notification;
import web.AuthorityManager;

@Controller
public class STOMPController {

	@Autowired
	private MessagesRepository messagesRepository;
	
	@Autowired
	private MessageFeedService messageFeedService;
	
	private static final Logger logger = LoggerFactory.getLogger(STOMPController.class);
	private String message = 
			"Pink Floyd"+
			"		   /       ---  "+
			"	------/  ---        "+
			"	-----/    --------- "+
			"  -----/      ---___   "+
			"	   /             ---"+
			"	  /__________       "+
			"unknown";
	
	@MessageMapping("/stompMessage")
	@SendTo("/topic/getMessage")
	public MessagePOJO handleMessage(MessagePOJO message){
		logger.info("Incoming message: "+message.getMessage());
		logger.info("Sending message: "+this.message);
		return new MessagePOJO(this.message);
	}
	
	@SubscribeMapping("/getStompMessage")
	public MessagePOJO handleSubscription(){
		return new MessagePOJO("hello world");
	}
	
	@MessageMapping("/postmessage")
	@SendToUser("/queue/notifications")
	public Notification saveMessage(Principal principal, MessageForm messageForm) {
		
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		if(messageForm.getUsername() == null) 
			messageForm.setUsername(principal.getName());
		
		if(messageForm.getMessage().toCharArray().length == 0 || 
				messageForm.getMessage().toCharArray().length > 777){
			throw new MessageException("FAIL. Try again.");
		}else if(messageForm.getUsername().toCharArray().length < 2 || 
				messageForm.getUsername().toCharArray().length > 32){
			throw new MessageException("FAIL. Try again.");
		}
		
		Message message = new Message(
				messageForm.getMessage(),
				new Date(),
				messageForm.getLatitude(),
				messageForm.getLongitude(),
				messageForm.getUsername());
		
		this.messageFeedService.broadcastMessage(this.messagesRepository.save(message));
		return new Notification("Message was saved.");
	}
	
	@MessageExceptionHandler(MessageException.class)
	@SendToUser("/queue/errors")
	public MessageException handleException(MessageException me){
		logger.error("Error has accrued: "+me.getMessage());
		return me;
	}
}
