package alert;

import objects.Message;

public interface AlertService {
	public void sendObjectAlert(Object messageToSend);
	public String receiveTextMessage();
	
}
