package fr.cyu.coffeeclasses.spring.model.element;

import fr.cyu.coffeeclasses.spring.model.user.Student;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	// Which student ?
	@ManyToOne
	private Student student;

	// To what course ?
	@ManyToOne
	private Course course;

	// Grades for this enrollment
	@OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Grade> grades;

	/*
		Methods
	 */
	protected Enrollment() {}
	public Enrollment(Student student, Course course) {
		setStudent(student);
		setCourse(course);
	}

	// ID
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}

	// Student
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	// Course
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

	// Grades
	public Set<Grade> getGrades() {
		return grades;
	}
	public void addGrade(Assessment assessment, double value) {
		grades.add(new Grade(assessment, this, value));
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
}
