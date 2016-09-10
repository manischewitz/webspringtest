package tests;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import web.HomeController;

@WebAppConfiguration
public class HomeControllerTest {

	@Test
	public void test() throws Exception{
		System.out.print(Files.exists(Paths.get("WEB-INF/views/home.jsp")));
		HomeController home = new HomeController();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(home).build();
		  
		 mockMvc.perform(get("/"))
         .andExpect(view().name("home"));
	}

}

