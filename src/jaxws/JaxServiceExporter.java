package jaxws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

@Configuration
public class JaxServiceExporter {

	@Bean
	public SimpleJaxWsServiceExporter exporter(){
		SimpleJaxWsServiceExporter jax = new SimpleJaxWsServiceExporter();
		jax.setBaseAddress("http://localhost:9999/services/");
		return jax;
	}
}
