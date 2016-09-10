package email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import objects.Message;
@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	
	
	@Override
	public void sendMessage(String to, String from, Message message) throws javax.mail.MessagingException {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime,true);
		String messageName = message.getUsername();
		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject("New message from "+messageName);
		FileSystemResource resource = 
				new FileSystemResource("A:/workspacemars/EEProject/WebContent/resources/images/2885a854440d11fdd37e96da0ed7e2c5.jpg");
		String emailText = 
				"<html><body><img src='cid:image'/><br/>"+
				"<h4>"+message.getUsername()+" says...</h4>"+
				"<i>"+message.getMessage()+"</i></body></html>";
		helper.setText(emailText,true);
		helper.addInline("image", resource);
		mailSender.send(mime);
	}

	@Override
	public void sendSimpleTextMessage(String to, String from, Message message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String spitterName = message.getUsername();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject("New message from " + spitterName);
		mailMessage.setText(spitterName + " says: " +message.getMessage());
		mailSender.send(mailMessage);
	}

}
