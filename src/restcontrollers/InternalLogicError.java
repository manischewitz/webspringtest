package restcontrollers;

@SuppressWarnings("serial")
public class InternalLogicError extends RuntimeException{

	private String cause;
	
	public InternalLogicError(String cause){
		this.cause =cause;
	}

	public String cause() {
		return this.cause;
	}

	
}
