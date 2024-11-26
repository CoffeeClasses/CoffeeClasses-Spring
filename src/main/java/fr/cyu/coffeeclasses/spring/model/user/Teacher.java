package fr.cyu.coffeeclasses.spring.model.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import fr.cyu.coffeeclasses.spring.model.element.Assessment;
import fr.cyu.coffeeclasses.spring.model.element.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class Teacher extends User {
	/*
		Fields
	 */
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Course> courses = new HashSet<>();

	/*
		Methods
	 */
	protected Teacher() {}
	public Teacher(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}

	// Courses
	public Set<Course> getCourses() {
		return courses;
	}
	public void addCourse(Course course) {
		courses.add(course);
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	// Assessments
	public Set<Assessment> getAssessments() {
		Set<Assessment> assessments = new HashSet<>();
		for (Course course : courses) {
			assessments.addAll(course.getAssessments());
		}
		return assessments;
	}
}
