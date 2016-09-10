package objects;

import java.io.Serializable;
import java.sql.Timestamp;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="newUsers")
public class User implements Serializable{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", unique = true, updatable = true)
	@NotNull
	@Size(min=2,max=32 , message="{username.size}")
	private String username;
	
	@Column(name="password")
	@NotNull
	@Size(min=6,max=128,message="{password.size}")
	private String password;
	
	@Column(name="first_name")
	@Size(min=0,max=32)
	private String firstName;
	
	@Column(name="last_name")
	@Size(min=0,max=32)
	private String lastName;
	
	@Column(name="email")
	@NotNull
	@Size(min=6,max=32)
	@Email(message="{email.valid}")
	private String email;
	
	@Column(name="role" )
	private String role;
	
	@Column(name="registration_date")
	private Date registration_date;
	
	public User(String username, String password, String firstName, String lastName, String email){
		this(null, username, password, firstName, lastName, email);
	}
	
	public User(){}
	
	public User(Long id, String username, String password, String firstName, String lastName, String email,String role,Date registration_date){
		this.id = id;
		this.username = username;
		this.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.setEmail(email);
		this.setRole(role);
		this.setRegistration_date(registration_date);
	}
	
	@Deprecated
	public User(Long id ,String username, String password){
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public User(Long id, String username, String password, String firstName, String lastName, String email){
		this.id = id;
		this.username = username;
		this.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.setEmail(email);
	}
	
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = new StandardPasswordEncoder().encode(password);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean equals(Object that){
		return EqualsBuilder.reflectionEquals(this, that, new String[] {"id","login", "email"});
	}
	
	public int hashCode(){
		return HashCodeBuilder.reflectionHashCode(this,new String[] {"id","login", "email"});
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

}
