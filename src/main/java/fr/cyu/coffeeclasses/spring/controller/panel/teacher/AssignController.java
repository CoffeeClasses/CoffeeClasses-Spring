package fr.cyu.coffeeclasses.spring.controller.panel.teacher;

import fr.cyu.coffeeclasses.spring.dto.teacher.AssessmentRequestDTO;
import fr.cyu.coffeeclasses.spring.model.element.Assessment;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import fr.cyu.coffeeclasses.spring.repository.AssessmentRepository;
import fr.cyu.coffeeclasses.spring.repository.CourseRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AssignController {
	private static final String JSP_PATH = "panel/teacher/assessment";

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AssessmentRepository assessmentRepository;

	@GetMapping("/panel/teacher/assessments")
	public String showPage(HttpServletRequest request) {
		Teacher teacher = (Teacher) request.getAttribute("user");
		request.setAttribute("teacher", teacher);
		return JSP_PATH;
	}

	@PostMapping("/panel/teacher/assessments")
	public String registerAssessment(AssessmentRequestDTO dto, HttpServletRequest request) {
		assessmentRepository.save(new Assessment(
			dto.getName(),
			dto.getDate(),
			dto.getMaxGrade(),
			courseRepository.findById(dto.getSelectedCourse()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cours not trouvé"))
		));
		request.setAttribute("successMessage", "L'évaluation a bien été enregistrée.");
		return "redirect:/panel/teacher/assessments";
	}
}
