import static org.junit.Assert.*;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Test {

	@org.junit.Test
	public void test() {
		
		System.out.println(new StandardPasswordEncoder().encode("test"));  
		
		
	}

}
