package web;

import java.io.IOException;

import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/imageUpload")
public class ImageUploadController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String showView(){
		
		return "imageUpload";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String upload(@RequestPart("picture") Part picture ){
		
		//validation here
		//set new name to file
		try {
			picture.write("A://workspacemars//EEProject//downloads//"+picture.getSubmittedFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/homepage";
	}

}
