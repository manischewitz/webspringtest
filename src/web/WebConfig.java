package web;
//eeproject
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;						
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import jms.JMSMessagesImpl;

@Configuration
@EnableWebMvc
@ComponentScan({
	"web",
	"db",
	"rmi",
	"jaxws",
	"restcontrollers",
	"jms",
	"alert",
	"websocket",
	"stomp",
	"email",
	"mbeans"})

public class WebConfig extends WebMvcConfigurerAdapter{

	/*@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setPrefix("/WEB-INF/views/");
		irvr.setSuffix(".jsp");
		irvr.setExposeContextBeansAsAttributes(true);
		return irvr;
	}*/
	
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tc = new TilesConfigurer();
		tc.setDefinitions(new String[] {"/WEB-INF/views/tiles.xml"});
		tc.setCheckRefresh(true);
		return tc;
	}
	
	@Bean
	public ViewResolver viewResolver(){
		return new TilesViewResolver();
	}
	
	/*@Bean
	public ViewResolver cnViewResolwer(ContentNegotiationManager cnm){
		ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
		cnvr.setContentNegotiationManager(cnm);
		
		return cnvr;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}*/
	
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource rbms = new ReloadableResourceBundleMessageSource();
		
		rbms.setBasename("classpath:messages");
		rbms.setCacheSeconds(10);
		return rbms;
	}
	
	@Bean
	public MultipartResolver multipartResolver() throws IOException{
		return new StandardServletMultipartResolver();
	}
	
	
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}
	
	@Bean 
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL("tcp://localhost:61616");
		return factory;
	}
	
	
	@Bean 
	public ActiveMQQueue queue(){
		ActiveMQQueue queue = new ActiveMQQueue("AppQueue");
		return queue;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.
		addResourceHandler("/webjars/**").
		addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	
	
	//eeproject
	
	
	
	
}
