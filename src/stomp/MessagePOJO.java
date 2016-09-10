package stomp;

public class MessagePOJO {

	private String message;

	public MessagePOJO(String message){
		this.message = message;
	}
	
	public MessagePOJO(){}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
