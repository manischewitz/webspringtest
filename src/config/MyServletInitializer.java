package config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;

import servlets.MainPageServlet;

public class MyServletInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		
		Dynamic servlet = servletContext.addServlet("mainPageServlet", MainPageServlet.class);
		servlet.addMapping("/main");

	}

}
