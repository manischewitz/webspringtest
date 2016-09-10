package jms;

import objects.Message;

public interface JMSMessages {

	public void sendMessage(String message);
	public void receiveMessage();
	
}
