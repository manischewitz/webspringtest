package stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import db.MessagesRepository;
import objects.MessageForm;

@Controller
public class STOMPWebController {

	
	@Autowired
	MessagesRepository messagesRepository;
	
	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	
	@RequestMapping(value="/stomp",method=RequestMethod.GET)
	public String getStompPage(){
		return "stompExample";
	}
	
	@RequestMapping(value="/messagesfeed",method=RequestMethod.GET)
	public String getMessagesFeed(
			Model model,
			@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max, 
			@RequestParam(value="count", defaultValue="20") int count){
		model.addAttribute("messagesList", messagesRepository.findLastMessages(max, count));
		model.addAttribute("messageForm",new MessageForm());
		return "messagesfeed";
	}
}
