package servlets;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageLoader {

	private static final String HTML_DIR = "WEB-INF/views"; 
	
	private static PageLoader pl;
	private final Configuration cgf;
	
	public static PageLoader instance(){
		if(pl == null) 
			pl = new PageLoader();
		return pl;
	}
	
	public String getPage(String filename, Map<String, Object> vars){
		
		Writer stream = new StringWriter();
		
		try{
			Template template = cgf.getTemplate(HTML_DIR + File.separator + filename);
			
			template.process(vars, stream);			
		}catch(IOException | TemplateException e){e.printStackTrace();}
		
		return stream.toString();
	}
	
	private PageLoader(){
		cgf = new Configuration();
	}
	
}
