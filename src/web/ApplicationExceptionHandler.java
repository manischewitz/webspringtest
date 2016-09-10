package web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import exceptions.AccessDeniedException;
import exceptions.DuplicateMessageException;
import exceptions.MessageNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(DuplicateMessageException.class)
	public String handleDuplicateMessage(){
		return "errors:duplicate";
	}
	
	@ExceptionHandler(MessageNotFoundException.class)
	public String handleNotFoundMessage(){
		return "errors:MessageNotFound";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDenied(){
		return "errors:accessdenied";
	}
}
