package fr.cyu.coffeeclasses.spring.controller.panel.student;

import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import fr.cyu.coffeeclasses.spring.service.PdfService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/panel/student/details")
public class StudentSummaryController {
	private static final String JSP_PATH = "panel/student/student-summary";

	@Autowired
	private PdfService pdfService;

	@GetMapping("")
	public String showStudentSummary(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");

		if (user instanceof Student student) {
			request.setAttribute("target", student);
			return JSP_PATH;
		} else {
			// Unnecessary, the filter should've caught non-students anyways.
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This page is only for students");
		}
	}

	@PostMapping("")
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getAttribute("user");

		if (user instanceof Student student) {
			try {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=notes-etudiant-" + user.getId() + ".pdf");
				pdfService.generateStudentSummary(student, response.getOutputStream());
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la génération du PDF", e);
			}
		} else {
			// Unnecessary, the filter should've caught non-students anyways.
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This page is only for students");
		}
	}
}
