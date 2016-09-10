package rmi;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;

@Configuration
public class RemoteMethodInvocationSpringConfig {
	
	@Autowired
	ApplicationService applicationService ;

	@Bean
	public  RmiServiceExporter  rmiReporter(ApplicationService applicationService){
		RmiServiceExporter rse = new RmiServiceExporter();
		rse.setService(applicationService);
		rse.setServiceName("ApplicationService");
		rse.setServiceInterface(ApplicationService.class);
		return rse;
	}


	
	// start RMI in linux: rmiregistry
}
