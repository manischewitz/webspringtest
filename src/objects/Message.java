package objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
@SuppressWarnings("serial")
@Entity
@Table(name="messages")
public class Message implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private  Long id;
	
	@Column(name="posted_by")
	@Size(min=2, max=32)
	@NotNull
	private String posted_by;
	
	@Column(name="message")
	@NotNull
	@Size(min=1,max=512,message="{message.size}")
	private  String message;
	
	@Column(name="post_date")
	private Date time;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	public Message(){}
	
	public Message(String message, Date time, Double latitude,Double longitude){
		this.id = null;
		this.message =  message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Message(String message, Date time, Double latitude,Double longitude,String posted_by){
		this.posted_by = posted_by;
		this.id=null;
		this.message = message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	@Deprecated
	public Message(String message, Date time, long id){
		this.id = id;
		this.message =  message;
		this.time = time;
	}
	
	public Message(String message, Date time, Double latitude,Double longitude, Long id,String posted_by){
		this.id = id;
		this.message =  message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
		this.posted_by = posted_by;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public boolean equals(Object that){
		return EqualsBuilder.reflectionEquals(this, that, new String[] {"id","time"});
	}
	
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this,new String[] {"id","time"});
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getUsername() {
		return posted_by;
	}

	public void setUsername(String username) {
		this.posted_by = username;
	}
}
