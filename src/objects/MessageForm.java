package objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class MessageForm {

	
	@Size(min=2, max=32,message="{message.username}")
	private String username;
	
	@NotNull()
	@Size(min=1,max=777, message="{message.size}")
	private String message;
	
	@Min(-180)
	@Max(180)
	private Double longitude;
	  
	@Min(-90)
	@Max(90)
	private Double latitude;

	public MessageForm(String message, Double longitude, Double latitude){
		this.message=message;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public MessageForm(String message){
		this.message = message;
	}
	
	public MessageForm(){}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	
}
