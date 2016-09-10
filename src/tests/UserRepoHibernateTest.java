package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import db.HibernateConfig;
import db.UserRepoHibernate;
import db.UserRepository;
import objects.User;

public class UserRepoHibernateTest {

	private UserRepoHibernate ur = new UserRepoHibernate();
	private HibernateConfig hc = new HibernateConfig();
	
	@Test
	public void shouldReturnUserList() {
		ur.setSessionFactory(hc.createSessionFactory());
		
		List<User> userList = ur.findAll();
		assertNotNull(userList);
		System.err.println(userList.get(0).getEmail());
		System.err.println(userList.size());
	}

	@Test
	public void shouldFindByLogin() {
		ur.setSessionFactory(hc.createSessionFactory());
		
		Long id = ur.save(new User("TEST", "TEST", "TEST", "TEST", "TEST@TEST.TE")).getId();
		User user = ur.findByLogin("TEST");
		assertEquals(user.getUsername(),"TEST");
		ur.delete(id);
	}
	
	@Test
	public void shouldUpdateUser() throws InterruptedException{
		ur.setSessionFactory(hc.createSessionFactory());
		String username = "TESTER"+Math.random()*1000;
		String email = "sunday@email.ema"+Math.floor(Math.random()*1000);
		User beforeSaveUser = new User(username,"pswrd", "taking", "back",email);
		
		User user = ur.save(beforeSaveUser);
	    Long id = user.getId();
		
		User afterSaveUser = new User(id,"NEW"+username,"NEWpswrd", user.getFirstName(), user.getLastName(),
				user.getEmail(),user.getRole(),user.getRegistration_date());
		
		ur.update(afterSaveUser);
		
		assertEquals("NEW"+username,ur.findOne(id).getUsername());
		//ur.delete(id);
		}
}
