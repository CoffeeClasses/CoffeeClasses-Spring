package fr.cyu.coffeeclasses.spring.controller.panel;

import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class UserShowController {
	private static final String JSP_PATH = "panel/user-show";

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/panel/users/show")
	public String showUser(@RequestParam(name = "id", required = false) String id, Model model) {
		if (id != null) {
			try {
				Optional<User> targetOpt = userRepository.findById(Long.valueOf(id));
				if (targetOpt.isPresent()) {
					model.addAttribute("target", targetOpt.get());
					System.out.println("Working...");
					return JSP_PATH;
				} else {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
				}
			} catch (NumberFormatException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user ID specified.");
		}
	}
}
