package tests;

import static org.hamcrest.Matchers.hasItems;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import db.MessagesRepository;
import db.UserRepository;
import objects.Message;
import objects.User;
import web.MessageController;
import web.RegisterController;

public class RegisterTest {

	@Test
	public void testShowRegistrationForm() throws Exception {
		UserRepository mockRepo = mock(UserRepository.class);
		RegisterController controller = new RegisterController(mockRepo);
		MockMvc mockmvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockmvc.perform(get("/user/register"))
		.andExpect(view().name("registerForm"));
		
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception{
		User unsaved = new User("Admin", "root123321","11one","11two", "email33l@mail.ru");
		User saved = new User((long) 2, "Admin", "root123321","11one","11two", "emai33l@mail.ru");
		
		UserRepository mockRepo = mock(UserRepository.class);
		
		when(mockRepo.save(unsaved)).thenReturn(saved);
		
		RegisterController controller = new RegisterController(mockRepo);
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMVC.perform(post("/user/register")
				.param("username", "Admin")
				.param("password", "root123321")
				.param("firstName", "11one")
				.param("lastName", "11two")
				.param("email", "email33l@mail.ru"))
		.andExpect(redirectedUrl("/user/Admin"));
		//its not recognize my db
		verify(mockRepo, atLeastOnce()).save(unsaved);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
