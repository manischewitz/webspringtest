package websocket;

import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class WebSocketController {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
	
	@RequestMapping(value="/websocket",method=RequestMethod.GET)
	public String webSocketPage(){
		logger.info("START RENDER /websocket PAGE.");
		return "websocket";
	}
	
}
