package restcontrollers;

@SuppressWarnings("serial")
public class MessageNotFoundException extends RuntimeException{

	private Long messageId;
	
	public MessageNotFoundException(Long messageId){
		this.messageId = messageId;
	}

	public Long getMessageId() {
		return messageId;
	}
}
