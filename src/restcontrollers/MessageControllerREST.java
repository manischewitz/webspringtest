package restcontrollers;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import db.MessagesRepository;


import objects.Message;
import objects.MessageForm;
import web.AuthorityManager;

@Controller
@RequestMapping("/messages-api")
public class MessageControllerREST {
	
	private MessagesRepository messagesRepository;
	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	
	@Autowired
	public MessageControllerREST(MessagesRepository messagesRepository){
		this.messagesRepository = messagesRepository;
	}
	
	
	
	
	
	@RequestMapping(method=RequestMethod.GET,produces ="application/json")
	public @ResponseBody List<Message> messages(
			@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max, 
			@RequestParam(value="count", defaultValue="20") int count){
		
		return messagesRepository.findLastMessages(max, count);
	}
	
	@RequestMapping(value="/{messageID}",method=RequestMethod.GET)
	public @ResponseBody Message message(@PathVariable("messageID") long messageID){
		
		Message message = messagesRepository.findOne(messageID);
		
		if(message == null) throw new MessageNotFoundException(messageID);
		
		return message;
	}
	
	@RequestMapping(method=RequestMethod.POST,consumes="application/json")
	public ResponseEntity<Message> saveMessage( @RequestBody @Valid MessageForm form, Errors errors, UriComponentsBuilder uri) throws Exception {
		
		checkAuthority();
		if(errors.hasErrors()) throw new NotCorrectMessage();
		String username = AuthorityManager.instance().getCurrentUsername();
		Message savedMessage = messagesRepository.save(new Message
				(form.getMessage(), new Date(), form.getLatitude(), form.getLongitude(),username));;
		
		HttpHeaders headers = new HttpHeaders();
		 URI locationUri = uri.
				 path("/messages-api/").
				 path(String.valueOf(savedMessage.getId())).
				 build().
				 toUri();
		headers.setLocation(locationUri);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		ResponseEntity<Message> responseEntity = 
				new  ResponseEntity<Message>(savedMessage,headers,HttpStatus.CREATED);
		
		return responseEntity;
	}
	
	@RequestMapping(value="/csrf-token", method=RequestMethod.GET,consumes="application/json")
	public @ResponseBody ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request) {
	    CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-XSRF-TOKEN", token.getToken());
		headers.set("Content-Type", "application/json");
		ResponseEntity<CsrfToken> response = 
				new ResponseEntity<CsrfToken>(token,headers,HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE,consumes="application/json")
	public @ResponseBody ResponseEntity<Message> deleteMessage(@PathVariable("id") String id){
		checkAuthority();
		long messageID = Long.parseLong(id);
		
		Message messageToDelete = messagesRepository.findOne(messageID);
		 
		if(messageToDelete == null) throw new MessageNotFoundException(messageID);
		
		if(AuthorityManager.instance().compareGivenUsernameWithPrincipalUsername(messageToDelete.getUsername())
	|| AuthorityManager.instance().isAuthority("root")){
			messagesRepository.delete(messageID);
		}else throw new AccessDeniedException();
		
		return new ResponseEntity<Message>(messageToDelete,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT,consumes="application/json")
	public @ResponseBody ResponseEntity<Message> updateMessage(
			@PathVariable("id") String id,
			@RequestBody @Valid Message messageToUpdate,
			Errors errors){
		checkAuthority();
		if(errors.hasErrors()) throw new NotCorrectMessage();
		
		long messageID = Long.parseLong(id);
		
		if(messageID != messageToUpdate.getId()) throw new InternalLogicError(
				"Path variable id and message id are different.");
		
		Message messageBefore = messagesRepository.findOne(messageID);
		
		if(messageBefore == null) throw new MessageNotFoundException(messageID);
		
		if(AuthorityManager.instance().compareGivenUsernameWithPrincipalUsername(messageBefore.getUsername())
				|| AuthorityManager.instance().isAuthority("root")){
						messagesRepository.updateMessage(messageToUpdate);
		}else throw new AccessDeniedException();
					
		return new ResponseEntity<Message>(messageBefore,HttpStatus.OK);
	}
	
	private void checkAuthority(){
		if(!AuthorityManager.instance().hasAuthorities(new String[] {"user","root"}))
			throw new AccessDeniedException();
	}
	
	@ExceptionHandler(MessageNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Error messageNotFound(MessageNotFoundException mnfe){
		Long id = mnfe.getMessageId();
		Error error = new Error(1,"Message "+id+" not found.");
		return error; 
	}
	
	@ExceptionHandler(NotCorrectMessage.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Error notCorrectMessage(){
		Error error = new Error(2,"Some field is incorrect.");
		return error;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody Error accessDenied(){
		Error error = new Error(3,"Access denied. Check your authority.");
		return error;
	}
	
	@ExceptionHandler(InternalLogicError.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody Error internalLogicError(InternalLogicError ile){
		Error error = new Error(1000,"Internal Logic Error with cause: "+ile.cause());
		return error;
	}
	
	
	
	
	
	
	
	
}
