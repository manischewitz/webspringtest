package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jms.JMSMessages;

@Controller
@RequestMapping("/ebay")
public class EbayController {
	
	@Autowired
	JMSMessages jmsMessages;

	@RequestMapping(method=RequestMethod.GET)
	public String ebay(){
		jmsMessages.sendMessage("HALLO WORLD!!!1");
		//jmsMessages.receiveMessage();
		return "ebay";
	}
	
	
}
