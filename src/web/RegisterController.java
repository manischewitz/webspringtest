package web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import db.UserRepository;
import objects.User;

@Controller
@RequestMapping("/user")
public class RegisterController {

	private UserRepository userRepository;
	
	@Autowired
	public RegisterController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegistrationForm(Model model){
		model.addAttribute("user",new User());
		return "registerForm";
	}
	
	/*@RequestMapping(value="/register",method=RequestMethod.POST)
	public String redirect(@Valid User user, Model data, Errors errors){
		data.addAttribute("username", user.getUsername());
		data.addAttribute("userid", user.getId());
		
		if(errors.hasErrors()) return "registerForm";
		
		userRepository.save(user);
		return "redirect:/user/{username}";
	}*/
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String redirect(@Valid User user,Errors errors, RedirectAttributes data ){
		
		if(errors.hasErrors()) {return "registerForm";}
		
		data.addAttribute("username", user.getUsername());
		data.addFlashAttribute("user", user);
		
		userRepository.save(user);
		return "redirect:/user/{username}";
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showUserProfile(@PathVariable String username, Model model){
		
		if(model.containsAttribute("user")){
			model.addAttribute("user",userRepository.findByLogin(username));
		}
		return "profile";
	}
	
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public String me(Model model){
		return "profile";
	}
	
	
	
	
	
	
}
