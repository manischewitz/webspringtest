package config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import objects.User;

import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	static final Logger logger = LogManager.getLogger(SecurityConfig.class.getName());
	
	protected void configure(HttpSecurity http) throws Exception{
		http
		.authorizeRequests()
			.antMatchers("/user/me", "/ebay").authenticated()
			.antMatchers(HttpMethod.POST,"/messages").hasAnyAuthority("user","root")
			.antMatchers(HttpMethod.POST,"/messages-api").hasAnyAuthority("user","root")
			.anyRequest().permitAll()
		
		.and()
			.requiresChannel()
			.antMatchers("/","/homepage").requiresInsecure()
			.antMatchers("/user/register").requiresInsecure()
		.and()
			.formLogin()
			.loginPage("/login")
		.and()
			.rememberMe()
			.tokenValiditySeconds(60)
			.key("userKey")
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/homepage")
			
		.and()
			.httpBasic()
			.realmName("application")
		.and()
			.csrf()
			.csrfTokenRepository(csrfTokenRepository());
		
	}
	
	/*protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.inMemoryAuthentication()
		.withUser("user").password("pass").roles("USER")
		.and().withUser("root").password("root").roles("USER","ADMIN");
	}*/
	
	
	
	@Bean
	protected CsrfTokenRepository csrfTokenRepository()
	{
	    CookieCsrfTokenRepository s = new CookieCsrfTokenRepository();
	    s.setHeaderName("X-XSRF-TOKEN");
	    s.setCookieHttpOnly(false);
	    
	    
	    return s;
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT username,password,true FROM newUsers WHERE username=?")
		.authoritiesByUsernameQuery("SELECT username,role FROM newUsers WHERE username=?")
		.passwordEncoder(new StandardPasswordEncoder());
	}
	
	/**
	 * JNDI really shines when you have to move an application between environments: development to integration to test to production. 
	 * If you configure each app server to use the same JNDI name, you can have different databases in each environment and not have to change your code. 
	 * You just pick up the WAR file and drop it in the new environment.

	Here are some other assumptions that are crucial to know when judging this answer:

	I don't have access to the servers on which the code is deployed at all, except for read-only access to logs.
	The person who writes and packages the code is not the same person who configures and manages the server.
	Once a WAR file starts on its journey to PROD it cannot be changed again without going back to the beginning. Any testing that's done by QA on the test server must be re-done if the WAR is altered.
	Perhaps you don't see this benefit because you're a lone developer who writes code on a local desktop and deploys right to production.
	*/
	
	@Profile("jdbc")
	@Bean(name="dataSource")
	public DriverManagerDataSource JDBCDriverDataSource() throws InterruptedException{
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		/*dmds.setDriverClassName("com.mysql.jdbc.Driver");
		dmds.setUrl("jdbc:mysql://localhost:3306/eeProjectDB");*/
		dmds.setDriverClassName("org.h2.Driver");
		dmds.setUrl("jdbc:h2:tcp://localhost/~/users");
		dmds.setUsername("root");
		dmds.setPassword("root");
		System.out.println("You whithin jdbc profie");
		
		return dmds;
	}
	
	@Profile("production")
	@Bean(name="dataSource")
	public BasicDataSource DBCPDriverDataSource() throws InterruptedException{
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/eeProjectDB");
		bds.setUsername("root");
		bds.setPassword("root");
		bds.setInitialSize(5);
		bds.setMaxActive(10);
		System.err.println("You whithin prod profie");
		return bds;
	}
	
	@Profile({"development","hibernate"})
	@Bean(name="dataSource")
	public SingleConnectionDataSource h2DataSource(){
		logger.info("You whithin dev profie");
		SingleConnectionDataSource scds = new SingleConnectionDataSource();
		scds.setDriverClassName("org.h2.Driver");
		scds.setUrl("jdbc:h2:tcp://localhost/~/users");
		scds.setUsername("root");
		scds.setPassword("root");
		scds.setSuppressClose(true);
		return scds;
	}
}


