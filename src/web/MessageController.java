package web;

import java.security.Principal;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import alert.AlertService;
import db.MessagesRepository;
import exceptions.AccessDeniedException;
import exceptions.DuplicateMessageException;
import exceptions.MessageNotFoundException;
import objects.Message;
import objects.MessageForm;
import stomp.MessageFeedService;

@Controller
@RequestMapping("/messages")
public class MessageController {

	public static final int DEFAULT_MESSAGES_PER_PAGE = 20;
	private int messagesPerPage = DEFAULT_MESSAGES_PER_PAGE;
	
	
	private MessagesRepository messagesRepository;
	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private MessageFeedService messageFeedService;
	
	@Autowired
	public MessageController(MessagesRepository messagesRepository){
		this.messagesRepository = messagesRepository;
		
	}
	
	@RequestMapping(value="/delete/{messageID}",method=RequestMethod.GET)
	public String deleteMessage(@PathVariable("messageID") long messageID){
		
		if(!AuthorityManager.instance().hasAuthorities(new String[] {"user","root"}))
			throw new AccessDeniedException();
		
		Message message = messagesRepository.findOne(messageID);
		 
		if(message == null) throw new MessageNotFoundException();
		
		if(AuthorityManager.instance().compareGivenUsernameWithPrincipalUsername(message.getUsername())
	|| AuthorityManager.instance().isAuthority("root")){
			messagesRepository.delete(messageID);
		}else throw new AccessDeniedException();
		
		return "redirect:/messages";
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String messages(
			@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max, 
			@RequestParam(value="count"/*, defaultValue=this.getMessagesPerPage()*/, required=false) Integer count, 
			Model data){ 
		if(count == null) count = this.getMessagesPerPage();
		
		data.addAttribute("messageForm",new MessageForm());
		data.addAttribute("messagesList",messagesRepository.findLastMessages(max, count));
		return "messages";
	}
	
	@RequestMapping(value="/{messageID}",method=RequestMethod.GET)
	public String message(@PathVariable("messageID") long messageID, Model model){
		
		Message message = messagesRepository.findOne(messageID);
		
		if(message == null) throw new MessageNotFoundException();
		model.addAttribute("message", message);
		return "message";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveMessage(@Valid MessageForm form, Errors errors) throws InterruptedException {
		
		 AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		
		if(form.getUsername() == null) form.setUsername(AuthorityManager.instance().getCurrentUsername());
		 
		 if(errors.hasErrors()) return "messages";
		 
		 Message message = new Message(
				 form.getMessage(),
				 new Date(),
				 form.getLatitude(),
				 form.getLongitude(),
				 form.getUsername());
		
		messagesRepository.save(message);
		
		
		alertService.sendObjectAlert(message);
		messageFeedService.broadcastMessage(message);
		
		return "redirect:/messages";
	}

	public int getMessagesPerPage() {
		return messagesPerPage;
	}

	public void setMessagesPerPage(int messagesPerPage) {
		this.messagesPerPage = messagesPerPage;
		
	}
}