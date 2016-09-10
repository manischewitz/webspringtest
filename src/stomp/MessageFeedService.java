package stomp;

import java.security.Principal;

import objects.Message;
import objects.MessageForm;
import objects.Notification;

public interface MessageFeedService {
	public void broadcastMessage(Message message);
}
