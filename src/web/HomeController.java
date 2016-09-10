package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import alert.AlertService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping({"/","/homepage"})
public class HomeController {

	@RequestMapping(method=GET)
	public String home(){
		return "home";
	}
	
	
}
