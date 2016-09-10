package tests;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import db.MessagesRepository;
import db.UserRepoImpl;
import db.UserRepository;
import objects.Message;
import objects.User;
import web.MessageController;
import web.RegisterController;

public class UserRepoTest {

	private UserRepoImpl repo = new UserRepoImpl();
	
	@Test 
	public void shouldUpdateUserData() throws InterruptedException{
		repo.setDataSource(this.embeddedDataSource());
		Double random = Math.floor(Math.random()*1000);
		User beforeSaveUser = new User("usrnme"+random,"pswrd", "taking", "back","sunday@email.ema"+random);
		
	    Long id = repo.save(beforeSaveUser).getId();
		
		User afterSaveUser = new User(id,"NEWusrnme","NEWpswrd", "taking", "back","sunday@email.ema"+random);
		
		repo.update(afterSaveUser);
		
		assertEquals("NEWusrnme",repo.findOne(id).getUsername());
		repo.delete(id);
		
	}
	
	
	@Test
	public void shouldFindUser(){
		repo.setDataSource(this.embeddedDataSource());
		double uniqueNum = Math.random()*1000;
		String username = "tester@test"+uniqueNum;
		User user = new User(null, username, "tester", "t", "t", "tester@test"+uniqueNum,null,null);
		Long id = repo.save(user).getId();
		
		assertEquals(username, repo.findOne(id).getUsername());
		assertEquals(id , repo.findOne(id).getId());
		assertEquals("t" , repo.findOne(id).getFirstName());
		assertEquals("t" , repo.findOne(id).getLastName());
		assertEquals(  "tester@test"+uniqueNum, repo.findOne(id).getEmail());
		assertEquals("user" , repo.findOne(id).getRole());
		
		
		assertEquals(username, repo.findByLogin(username).getUsername());
		assertEquals(id , repo.findByLogin(username).getId());
		assertEquals("t" , repo.findByLogin(username).getFirstName());
		assertEquals("t" , repo.findByLogin(username).getLastName());
		assertEquals(  "tester@test"+uniqueNum, repo.findByLogin(username).getEmail());
		assertEquals("user" , repo.findByLogin(username).getRole());
		repo.delete(id);
	}
	
	@Test
	public void shouldReturnAllUsers(){
		repo.setDataSource(this.embeddedDataSource());
		assertNotNull(repo.findAll());
	}
	

	private SingleConnectionDataSource embeddedDataSource(){
		SingleConnectionDataSource scds = new SingleConnectionDataSource();
		scds.setDriverClassName("org.h2.Driver");
		scds.setUrl("jdbc:h2:tcp://localhost/~/users");
		scds.setUsername("root");
		scds.setPassword("root");
		scds.setSuppressClose(true);
		return scds;
	}

}
