package objects;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tests.MessagesRepoTest;

public class JacksonJsonConverter {
	
	private static JacksonJsonConverter jacksonJsonConverter;
	static final Logger logger = LogManager.getLogger(JacksonJsonConverter.class.getName());
	private JacksonJsonConverter(){}
	
	public static JacksonJsonConverter instance(){
		if(jacksonJsonConverter == null) jacksonJsonConverter = new JacksonJsonConverter();
		return jacksonJsonConverter;
	}

	public String map(Object object){
		ObjectMapper mapper = new ObjectMapper();

		try {
			// Convert object to JSON string and save into a file directly
			//mapper.writeValue(new File("D:\\staff.json"), staff);

			// Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(object);
			logger.info("JSON >---"+jsonInString+"---<");
			// Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			logger.info("JSON >---"+jsonInString+"---<");
			return jsonInString;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object fromJson(String json){
		ObjectMapper mapper = new ObjectMapper();

		try {

			// Convert JSON string from file to Object
			//Object object = mapper.readValue(new File("D:\\staff.json"), Object.class);
			// Convert JSON string to Object
			Object obj = mapper.readValue(json, Object.class);
			return obj;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
