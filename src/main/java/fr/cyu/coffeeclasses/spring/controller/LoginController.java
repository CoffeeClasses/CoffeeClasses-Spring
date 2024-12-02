package fr.cyu.coffeeclasses.spring.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import fr.cyu.coffeeclasses.spring.dto.LoginRequestDTO;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.service.UserService;

import java.util.Optional;

@Controller
public class LoginController {
	private static final String JSP_PATH = "login";

	@Autowired
	private UserService authService;

	// Login

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String getLoginPage(HttpSession session) {
		if (session.getAttribute("user") == null) {
			return JSP_PATH;
		} else {
			return "redirect:/panel/home";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(LoginRequestDTO loginDTO, HttpSession session, Model model) {
		Optional<User> user = authService.authenticate(loginDTO);
		if(user.isPresent()){
			session.setAttribute("userId", user.get().getId());
			return "redirect:/panel/home";
		} else {
			model.addAttribute("errorMessage", "Mot de passe ou mail invalide.");
			return JSP_PATH;
		}
	}

	// Logout

	@RequestMapping(value = "/logout", method = { RequestMethod.GET,  RequestMethod.POST} )
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	// Fallback links

	@GetMapping(value = "/panel")
	public String panelHomeRedirect() {
		return "redirect:/panel/home";
	}
	@GetMapping(value = "/")
	public String rootRedirect() {
		return "redirect:/login";
	}
}
