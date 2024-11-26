package fr.cyu.coffeeclasses.spring.model.user;

import fr.cyu.coffeeclasses.spring.model.element.Course;
import fr.cyu.coffeeclasses.spring.model.element.Enrollment;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Student extends User {
	/*
		Fields
	 */
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Enrollment> enrollments = new HashSet<>();

	/*
		Methods
	 */
	protected Student() {}
	public Student(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}

	// Enrollments
	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(Set<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public void addEnrollment(Course course) {
		enrollments.add(new Enrollment(this, course));
	}
	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
	}

	/*
		Additional
	 */
	public boolean hasCourse(Course course) {
		for (Enrollment enrollment : enrollments) {
			if (enrollment.getCourse().equals(course)) {
				return true;
			}
		}
		return false;
	}

	public void setCourses(Set<Course> courses) {
		if (courses == null) throw new IllegalArgumentException("Courses set cannot be null");

		// Create a set of current courses the student is enrolled in
		Set<Course> currentCourses = new HashSet<>();
		for (Enrollment enrollment : enrollments) {
			currentCourses.add(enrollment.getCourse());
		}

		// Add enrollments for courses that are in the new set but not already enrolled
		for (Course course : courses) {
			if (!currentCourses.contains(course)) {
				addEnrollment(course);
			}
		}

		// Remove enrollments for courses that are no longer in the new set
		enrollments.removeIf(enrollment -> !courses.contains(enrollment.getCourse()));
	}
}
