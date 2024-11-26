package fr.cyu.coffeeclasses.spring.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import fr.cyu.coffeeclasses.spring.dto.LoginRequestDTO;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.service.LoginService;

import java.util.Optional;

@Controller
public class LoginController {
	private static final String JSP_PATH = "login";

	@Autowired
	private LoginService authService;

	// Login

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String getLoginPage() {
		return JSP_PATH;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(LoginRequestDTO loginDTO, HttpSession session, Model model) {
		Optional<User> user = authService.authenticate(loginDTO);
		if(user.isPresent()){
			session.setAttribute("userId", user.get().getId());
			return "redirect:/panel/home";
		} else {
			model.addAttribute("errorMessage", "Invalid email or password");
			return JSP_PATH;
		}
	}

	// Logout

	@RequestMapping(value = "/logout", method = { RequestMethod.GET,  RequestMethod.POST} )
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	// Fallback panel link

	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	public String redirect() {
		return "redirect:/panel/home";
	}
}
