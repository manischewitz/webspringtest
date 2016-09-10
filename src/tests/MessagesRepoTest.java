package tests;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import config.SecurityConfig;
import db.MessageRepoImpl;
import objects.Message;
import web.MessageController;

	


public class MessagesRepoTest {

	static final Logger logger = LogManager.getLogger(MessagesRepoTest.class.getName());
	private SecurityConfig sc = new SecurityConfig();
	private MessageRepoImpl mri = new MessageRepoImpl();
	
	@Test
	public void shouldFindMessages() {
		
		mri.setDataSource(sc.h2DataSource());
		
		final int count = 10;
		
		List<Message> messages = mri.findLastMessages(Long.MAX_VALUE, count);
		messages.forEach((Message m) -> logger.info(m.getId()));
		assertEquals(count,messages.size());
	}
	
	@Test
	public void shouldSaveMessageAndReturnItsId() throws Exception{
		mri.setDataSource(sc.h2DataSource());
		
		Message message = new Message("message",  null, 1.2,1.2, null,"tester");
		Long id = mri.save(message).getId();
		logger.info(id);
		assertNotNull(id);
	}
	
	@Test
	public void shouldReturnAllMessagesForUserAndThenDelete(){
		mri.setDataSource(sc.h2DataSource());
		final int count = 10;
		Long[] ids = new Long[count];
		double uniqUsername = Math.random()*1000;
		
		for(int i = 0;i<count;i++) ids[i] = mri.save(new Message("test",null, 1.1,2.2,"username"+uniqUsername)).getId();
		
		assertEquals(mri.getMessagesForUser("username"+uniqUsername).size(),count); 
		
		for(int i = 0;i<count;i++) mri.delete(ids[i]);
		
		assertEquals(0,mri.getMessagesForUser("username"+uniqUsername).size());
	}

}
