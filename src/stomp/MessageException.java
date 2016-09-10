package stomp;

@SuppressWarnings("serial")
public class MessageException extends RuntimeException{
		
	private String message;
	
	public MessageException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
