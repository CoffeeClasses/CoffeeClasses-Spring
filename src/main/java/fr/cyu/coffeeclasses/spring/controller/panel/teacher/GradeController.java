package fr.cyu.coffeeclasses.spring.controller.panel.teacher;

import fr.cyu.coffeeclasses.spring.ApplicationStartupRunner;
import fr.cyu.coffeeclasses.spring.dto.teacher.GradingRequestDTO;
import fr.cyu.coffeeclasses.spring.model.element.Assessment;
import fr.cyu.coffeeclasses.spring.model.element.Grade;
import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import fr.cyu.coffeeclasses.spring.repository.AssessmentRepository;
import fr.cyu.coffeeclasses.spring.repository.CourseRepository;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import fr.cyu.coffeeclasses.spring.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class GradeController {
	private static final String JSP_PATH = "panel/teacher/assessment-grading";
	private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

	@Autowired
	private AssessmentRepository assessmentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailService mailService;

	@GetMapping("/panel/teacher/grades")
	public String showPage(@RequestParam(name = "assessmentId") Long id, HttpServletRequest request) {
		// Assessment
		Assessment assessment = assessmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		// Teacher
		Teacher teacher = (Teacher) request.getAttribute("user");
		if (!assessment.getTeacher().getId().equals(teacher.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous ne possédez pas ce cours.");
		}

		// Collect grades
		Map<Student, Grade> studentGrades = new HashMap<>();
		for (Student student : assessment.getStudents()) {
			studentGrades.put(student, assessment.getGradeForStudent(student).orElse(null));
		}

		request.setAttribute("teacher", teacher);
		request.setAttribute("assessment", assessment);
		request.setAttribute("studentGrades", studentGrades);

		return JSP_PATH;
	}

	@PostMapping("/panel/teacher/grades")
	public String registerGrades(GradingRequestDTO dto, HttpServletRequest request) {
		// Find the assessment
		Assessment assessment = assessmentRepository.findById(dto.getAssessmentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));
		// Teacher
		Teacher teacher = (Teacher) request.getAttribute("user");
		// Ensure the teacher owns the assessment
		if (!assessment.getTeacher().getId().equals(teacher.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous ne possédez pas ce cours.");
		}

		// Process grades for each student
		dto.getGrades().forEach((studentId, gradeValue) -> {
			// Find the student by ID
			Student student = userRepository.findById(studentId)
					.filter(Student.class::isInstance) // Ensure it's a student
					.map(Student.class::cast)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Référence étudiant invalide : " + studentId));
			// Check if the student is part of the assessment
			if (assessment.getStudents().contains(student)) {
				// Update or create the grade
				Optional<Grade> existingGrade = assessment.getGradeForStudent(student);
				if (existingGrade.isPresent()) {
					Grade grade = existingGrade.get();
					grade.setValue(gradeValue);
				} else {
					assessment.getCourse().getEnrollmentForStudent(student)
							.ifPresent(enrollment -> {
								assessment.addGrade(enrollment, gradeValue);
							});
				}

				// Notify
				String message = "Votre note mise à jour pour l'évaluation: "+assessment.getName()+" ("+assessment.getCourse().getName()+") est disponible.\n"
						+ "Accédez à la plateforme CoffeeClasses pour la consulter.";
				mailService.sendMail(student, "Note disponible", message);
			} else {
				logger.warn("Student {} is not involved in assessment.", student.getId());
			}
		});

		// Save the updated assessment (and associated grades)
		assessmentRepository.save(assessment);

		request.setAttribute("successMessage", "Les notes ont bien été enregistrées.");
		return "redirect:/panel/teacher/assessments";
	}

}
