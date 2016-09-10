package tests;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import db.MessagesRepository;
import objects.Message;
import web.MessageController;

public class MessagesTest {

	@Test
	public void testShouldShowRecentMessages() throws Exception{
		List<Message> expectedMessages = createMessages(20);
		
		MessagesRepository mockMessagesRepository = mock(MessagesRepository.class);
		
		when(mockMessagesRepository.findLastMessages(Long.MAX_VALUE, 20)).thenReturn(expectedMessages);
		
		MessageController controller = new MessageController(mockMessagesRepository);
		
		MockMvc mockmvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("WEB-INF/views/messages.jsp")).build();
		
		mockmvc.perform(get("/messages"))
		.andExpect(view().name("messages"))
		.andExpect(model().attributeExists("messagesList"))
		.andExpect(model().attribute("messagesList", hasItems(expectedMessages.toArray())));
	}
	
	@Test
	public void testShouldShowMessages() throws Exception{
		List<Message> expectedMessages = createMessages(69);
		
		MessagesRepository mockMessagesRepository = mock(MessagesRepository.class);
		
		when(mockMessagesRepository.findLastMessages(777, 69)).thenReturn(expectedMessages);
		
		MessageController controller = new MessageController(mockMessagesRepository);
		
		MockMvc mockmvc = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("WEB-INF/views/messages.jsp")).build();
		
		mockmvc.perform(get("/messages?max=777&count=69"))
		.andExpect(view().name("messages"))
		.andExpect(model().attributeExists("messagesList"))
		.andExpect(model().attribute("messagesList", hasItems(expectedMessages.toArray())));
	}
	
	@Test
	public void testOneMessage() throws Exception{
		Message expectedMessage = new Message("TEST message",new Date(),256);
		MessagesRepository mockRepo = mock(MessagesRepository.class);
		when(mockRepo.findOne(256)).thenReturn(expectedMessage);
		
		MessageController controller = new MessageController(mockRepo);
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMVC.perform(get("/messages/256"))
		.andExpect(view().name("message"))
		.andExpect(model().attributeExists("message"))
		.andExpect(model().attribute("message", expectedMessage));
		
	}
	
	private List<Message> createMessages(int count){
		List<Message> messages = new ArrayList<>();
		for(int i = 0;i<count;i++){
			messages.add(new Message("Message "+i,new Date(),new Double(0),new Double(0)));
		}
		return messages;
	}
}
