package fr.cyu.coffeeclasses.spring.controller.panel.admin;

import fr.cyu.coffeeclasses.spring.dto.admin.*;
import fr.cyu.coffeeclasses.spring.model.element.Course;
import fr.cyu.coffeeclasses.spring.model.user.Administrator;
import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.CourseRepository;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;

@Controller
@RequestMapping("/panel/admin/courses")
public class CourseAdminController {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	// Search Course

	@GetMapping("")
	public String showSearchPage(CourseSearchRequestDTO dto, HttpServletRequest request) {
		Set<Course> courses = switch (dto.getSearch()) {
			case null -> new HashSet<>(courseRepository.findAll());
			default -> courseRepository.searchCourses(dto.getSearch());
		};
		// Sort
		Set<Course> sortedCourses = courses.stream()
				.sorted((course1, course2) -> course1.getName().compareToIgnoreCase(course2.getName()))
				.collect(Collectors.toCollection(LinkedHashSet::new));
		// Return
		request.setAttribute("courses", sortedCourses);
		return "panel/admin/course-management/course-list";
	}

	// Add course

	@GetMapping("/add")
	public String showAddPage(HttpServletRequest request) {
		request.setAttribute("teachers", userRepository.searchUsersByRoleAndString(Teacher.class, null));
		return "panel/admin/course-management/course-add";
	}

	@PostMapping("/add")
	public String addCourse(CourseAddRequestDTO dto, HttpServletRequest request) {
		Optional<User> user = userRepository.findById(dto.getTeacherId());
		if (user.isPresent()) {
			if (user.get() instanceof Teacher teacher) {
				courseRepository.save(
						new Course(
								dto.getName(),
								teacher
						)
				);
				return "redirect:/panel/admin/courses";
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'utilisateur lié n'est pas un professeur.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professeur inexistant.");
		}
	}

	// Delete courses

	@GetMapping("/delete")
	public String showDeletePage(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		Optional<Course> target = courseRepository.findById(id);
		if (target.isPresent()) {
			request.setAttribute("target", target.get());
			return "panel/admin/course-management/course-delete";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cours non trouvé.");
		}
	}

	@PostMapping("/delete")
	public String deleteUser(CourseDeleteRequestDTO dto) {
		Optional<Course> target = courseRepository.findById(dto.getId());
		if (target.isPresent()) {
			courseRepository.delete(target.get());
			return "redirect:/panel/admin/courses";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cours non trouvé.");
		}
	}

	// Edit

	@GetMapping("/edit")
	public String showEditPage(@RequestParam(name = "id") Long id, HttpServletRequest request) {
		Optional<Course> target = courseRepository.findById(id);
		if (target.isPresent()) {
			request.setAttribute("targetCourse", target.get());
			request.setAttribute("teachers", userRepository.searchUsersByRoleAndString(Teacher.class, null));
			return "panel/admin/course-management/course-edit";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cours non trouvé.");
		}
	}

	@PostMapping("/edit")
	public String editCourse(CourseEditRequestDTO dto, HttpServletRequest request) {
		Optional<Course> targetOpt = courseRepository.findById(dto.getId());
		if (targetOpt.isPresent()) {
			// Get teacher
			Teacher teacher = (Teacher) userRepository.findById(dto.getTeacher()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professeur non trouvé."));

			Course target = targetOpt.get();
			target.setName(dto.getName());
			target.setTeacher(teacher);

			courseRepository.save(target);
			return "redirect:/panel/admin/courses";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cours non trouvé.");
		}
	}
}
