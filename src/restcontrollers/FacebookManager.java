package restcontrollers;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class FacebookManager {

	public Profile fetchFacebookProfile(String id){
		RestTemplate rest = new RestTemplate();
		return rest.getForObject("http://graph.facebook.com/{profile-id}",Profile.class, id);
		//https://developers.facebook.com/docs/graph-api/reference/user
	}
	
	
}
