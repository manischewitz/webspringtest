package websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


public class TestWebSocketHandler implements  WebSocketHandler{

	private static final Logger logger = LoggerFactory.getLogger(TestWebSocketHandler.class);
	
	@Override
	public void afterConnectionClosed(WebSocketSession arg0, CloseStatus arg1) throws Exception {
		logger.info("Connection closed with status: "+arg1);
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession arg0) throws Exception {
		logger.info("Connection established.");
	}

	@Override
	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1) throws Exception {
		logger.info("Received message: " + arg1.getPayload());
		Thread.sleep(2000);
		arg0.sendMessage(new TextMessage("hallo"));
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		logger.info("FAIL.");
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
