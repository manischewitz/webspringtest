package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import config.MyServletInitializer;
import config.MyWebAppInitializer;
import config.RootConfig;
import db.HibernateConfig;
import db.MessagesRepoHibernate;
import db.MessagesRepository;
import db.UserRepository;
import objects.Message;
import web.MessageController;
import web.RegisterController;
import web.WebConfig;

public class MessagesRepoHibernateTest {

	@Test
	public void shouldReturnNmessages() throws Exception {
		
		final int N = 1;
		
		MessagesRepoHibernate mrh = new MessagesRepoHibernate();
		HibernateConfig hibernateConfig = new HibernateConfig();
		mrh.setSessionFactory(hibernateConfig.createSessionFactory());
		MessageController controller = new MessageController(mrh);
		
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(hibernateConfig,controller).build();
		
		mockMVC.perform(post("/login")
				.param("username", "tester").
				param("password", "tester") );
		
		for(int t = 0;t< N;t++){
			mockMVC.perform(post("/messages")
					.param("message", "hello"+t) )
			.andExpect(redirectedUrl("/messages"));
		}
		
		List<Message> messageList = mrh.findLastMessages(Long.MAX_VALUE ,  N);
		messageList.forEach((Message val) -> System.out.println(val.getMessage()));
		assertEquals( N,messageList.size());
	}

	@Test
	public void shouldReturnMessagesForUser() throws Exception{
		final int count = 10;
		
		MessagesRepoHibernate mrh = new MessagesRepoHibernate();
		HibernateConfig hibernateConfig = new HibernateConfig();
		mrh.setSessionFactory(hibernateConfig.createSessionFactory());
		
		List<Long> idList = new ArrayList<Long>();
		
		for(int t = 0;t<count;t++){
			idList.add(mrh.save(new Message("message"+t, new Date(), 22.7,11.6,"TEST")).getId());
		}
		
		List<Message> forUser = mrh.getMessagesForUser("TEST");
		
		forUser.forEach((Message m) -> {
			assertEquals(m.getUsername(),"TEST");
			System.out.println(m.getUsername());
		});
		
		for(int t = 0;t<idList.size();t++){
			mrh.delete(idList.get(t));
		}
	}
	
	@Test
	public void shouldUpdateUser() throws Exception{
		MessagesRepoHibernate mrh = new MessagesRepoHibernate();
		HibernateConfig hibernateConfig = new HibernateConfig();
		mrh.setSessionFactory(hibernateConfig.createSessionFactory());
		
		Message messageBefore = new Message("message before update", new Date(),0.0,0.0,"tester");
		Message afterSave = mrh.save(messageBefore);
		assertNotNull(afterSave);
		
		Message messageAfter = new Message("message after update", new Date(),0.0,0.0,"tester");
		messageAfter.setId(afterSave.getId());
		mrh.updateMessage(messageAfter);
		
		Message afterUpdate = mrh.findOne(messageBefore.getId());
		assertEquals("message after update",afterUpdate.getMessage());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
