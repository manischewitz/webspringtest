package mbeans;

import java.util.HashMap;
import java.util.Map;

import javax.management.NotificationListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;
import org.springframework.jmx.support.RegistrationPolicy;

import web.MessageController;

@Configuration
public class MBeanConfig {

	@Bean
	public MBeanExporter mbeanExporter(MessageController messageController){
		MBeanExporter exporter = new MBeanExporter();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("app:name=MessageController", messageController);
		exporter.setBeans(beans);
		exporter.setAssembler(this.assembler());
		exporter.setRegistrationPolicy(RegistrationPolicy.FAIL_ON_EXISTING);
		return exporter;
	}
	
	@Bean
	public InterfaceBasedMBeanInfoAssembler assembler() {
		InterfaceBasedMBeanInfoAssembler assembler = new InterfaceBasedMBeanInfoAssembler();
		assembler.setManagedInterfaces(new Class<?>[] { 
			MessageControllerInterface.class });
	return assembler;
	}
}
