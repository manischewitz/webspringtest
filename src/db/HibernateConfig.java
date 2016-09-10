package db;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import objects.Message;
import objects.User;

@Profile("hibernate")
@Component
public class HibernateConfig {

	static final Logger logger = LogManager.getLogger(HibernateConfig.class.getName());
	
	/*private HibernateConfig(){
		configuration = getMyConfiguration();
	}
	
	public static HibernateConfig  instantiate() throws InterruptedException{
		Thread.sleep(45345345);
		if(hibernateConfig == null) return new HibernateConfig();
			else return hibernateConfig;
	}*/
	
	public Configuration getMyConfiguration(){
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Message.class);
		configuration.addAnnotatedClass(User.class);
		
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:tcp://localhost/~/users");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        return configuration;
	}
	
	
	public void printConnectionInfo(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
			session.doWork(connection1 -> {
				logger.info("DB name: "+connection1.getMetaData().getDatabaseProductName());
				logger.info("DB version: "+connection1.getMetaData().getDatabaseProductVersion());
				logger.info("driver: "+connection1.getMetaData().getDriverName());
				logger.info("Autocommit: "+connection1.getAutoCommit());
			});
		session.close();
	}
	
	@Bean(name="sessionFactory")
	public SessionFactory createSessionFactory(){
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(this.getMyConfiguration().getProperties());
		ServiceRegistry serviseRegistry = ssrb.build();
		return this.getMyConfiguration().buildSessionFactory(serviseRegistry);
	}
}
