package fr.cyu.coffeeclasses.spring.controller.panel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	private static final String JSP_PATH = "panel/home";

	@GetMapping(value = "/panel/home")
	public String home() {
		return JSP_PATH;
	}
}