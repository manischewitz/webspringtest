package web;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import db.MessagesRepository;
import email.EmailService;

@Controller
public class MailSendingPageController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessagesRepository messagesRepository;
	
	@RequestMapping(value="/mailpage",method=RequestMethod.GET)
	public String sendingPage() throws MessagingException{
		this.emailService.sendMessage("icktfwih@gmail.com", "TEST", 
				this.messagesRepository.findOne(1));
		return "mailpage";
	}
	
	
	
}
