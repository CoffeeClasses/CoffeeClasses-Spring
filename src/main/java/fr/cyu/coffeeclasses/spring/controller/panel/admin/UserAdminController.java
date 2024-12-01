package fr.cyu.coffeeclasses.spring.controller.panel.admin;

import fr.cyu.coffeeclasses.spring.dto.admin.UserAddRequestDTO;
import fr.cyu.coffeeclasses.spring.dto.admin.UserDeleteRequestDTO;
import fr.cyu.coffeeclasses.spring.dto.admin.UserEditRequestDTO;
import fr.cyu.coffeeclasses.spring.dto.admin.UserSearchRequestDTO;
import fr.cyu.coffeeclasses.spring.model.element.Course;
import fr.cyu.coffeeclasses.spring.model.user.Administrator;
import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.CourseRepository;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import fr.cyu.coffeeclasses.spring.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/panel/admin/users")
public class UserAdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private MailService mailService;

	// Search user

	@GetMapping("")
	public String showSearchPage(UserSearchRequestDTO dto, HttpServletRequest request) {
		// Get role
		Optional<Class<? extends User>> role = Optional.ofNullable(dto.getRole()).flatMap(r -> switch (r.toLowerCase()) {
			case "student" -> Optional.of(Student.class);
			case "teacher" -> Optional.of(Teacher.class);
			case "administrator" -> Optional.of(Administrator.class);
			default -> Optional.empty();
		});
		// We provide back the users
		request.setAttribute("users", userRepository.searchUsersByRoleAndString(role.orElse(null), dto.getSearch()));
		return "panel/admin/user-management/user-search";
	}

	// Add user

	@GetMapping("/add")
	public String showAddPage() {
		return "panel/admin/user-management/user-add";
	}

	@PostMapping("/add")
	public String addUser(UserAddRequestDTO dto, HttpServletRequest request) {
		userRepository.save(
				switch(dto.getRole()) {
					case "student" -> new Student(
							dto.getFirstName(),
							dto.getLastName(),
							dto.getEmail(),
							dto.getPassword(),
							dto.getBirthDate()
					);
					case "teacher" -> new Teacher(
							dto.getFirstName(),
							dto.getLastName(),
							dto.getEmail(),
							dto.getPassword(),
							dto.getBirthDate()
					);
					case "administrator" -> new Administrator(
							dto.getFirstName(),
							dto.getLastName(),
							dto.getEmail(),
							dto.getPassword(),
							dto.getBirthDate()
					);
					default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rôle spécifié invalide.");
				}
		);
		return "redirect:/panel/admin/users";
	}

	// Delete user

	@GetMapping("/delete")
	public String showDeletePage(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		Optional<User> target = userRepository.findById(id);
		if (target.isPresent()) {
			request.setAttribute("target", target.get());
			return "panel/admin/user-management/user-delete";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.");
		}
	}

	@PostMapping("/delete")
	public String deleteUser(UserDeleteRequestDTO dto) {
		Optional<User> target = userRepository.findById(dto.getId());
		if (target.isPresent()) {
			userRepository.delete(target.get());
			return "redirect:/panel/admin/users";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.");
		}
	}

	// Edit user

	@GetMapping("/edit")
	public String showEditPage(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		Optional<User> target = userRepository.findById(id);
		if (target.isPresent()) {
			// Get available courses for students and teachers
			Set<Course> availableCourses = new HashSet<>(courseRepository.findAll());
			// Set JSP attributes
			request.setAttribute("target", target.get());
			request.setAttribute("availableCourses", availableCourses);
			return "panel/admin/user-management/user-edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.");
		}
	}

	@PostMapping("/edit")
	public String editUser(UserEditRequestDTO dto) {
		Optional<User> targetOpt = userRepository.findById(dto.getId());
		if (targetOpt.isPresent()) {
			User target = targetOpt.get();
			target.setFirstName(dto.getFirstName());
			target.setLastName(dto.getLastName());
			target.setBirthDate(dto.getBirthDate());
			target.setEmail(dto.getEmail());
			if (dto.getPassword() != null && !Objects.equals(dto.getPassword(), "")) {
				target.setPassword(dto.getPassword());
			}

			// Courses
			List<Long> courseIds = dto.getCourses();
			if (courseIds != null) {
				Set<Course> selectedCourses = new HashSet<>();

				for (Long courseId : courseIds) {
					Optional<Course> course = courseRepository.findById(courseId);
					if (course.isPresent()) {
						selectedCourses.add(course.get());
					} else {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tentative d'ajout à un cours n'existant pas.");
					}
				}

				if (target instanceof Student) {
					((Student) target).setCourses(selectedCourses);
				} else if (target instanceof Teacher) {
					((Teacher) target).setCourses(selectedCourses);
				}
			}

			// Save
			userRepository.save(target);
			mailService.sendMail(target, "Données mises à jour", "Bonjour,\nVos informations ont bien été mises à jour par la scolarité.");

			return "redirect:/panel/admin/users";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.");
		}
	}
}
