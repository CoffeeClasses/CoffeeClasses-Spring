package fr.cyu.coffeeclasses.spring.controller.panel.student;

import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class StudentSummaryController {
	private static final String JSP_PATH = "panel/student/student-summary";

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/panel/student/details")
	public String showStudentSummary(HttpServletRequest request, Model model) {
		User user = (User) request.getAttribute("user");

		if (user instanceof Student student) {
			model.addAttribute("target", student);
			return JSP_PATH;
		} else {
			// Unnecessary, the filter should've caught non-students anyways.
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This page is only for students");
		}
	}
}
