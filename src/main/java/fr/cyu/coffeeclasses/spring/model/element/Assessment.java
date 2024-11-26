package fr.cyu.coffeeclasses.spring.model.element;

import fr.cyu.coffeeclasses.spring.model.user.Student;

import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "assessments")
public class Assessment {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Course course;

	// When did the assessment occur ?
	@Column(nullable = false)
	private LocalDate date;

	// What are its grades ?
	@OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Grade> grades = new HashSet<>();

	// What's the maximum value a grade can have on this assignment ?
	@Column(nullable = false)
	private int maximum;

	/*
		Methods
	 */
	protected Assessment() {}
	public Assessment(String name, LocalDate date, int maximum, Course course) {
		setName(name);
		setDate(date);
		setMaximum(maximum);
		setCourse(course);
	}

	// ID
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}

	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// Course
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

	// Date
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	// Grades
	public Set<Grade> getGrades() {
		return grades;
	}
	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}
	public void addGrade(Enrollment enrollment, double value) {
		grades.add(new Grade(this, enrollment, value));
	}

	// Maximum value
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	/*
		Bonus
	 */
	public Teacher getTeacher() {
		return course.getTeacher();
	}
	public Set<Student> getStudents() {
		return course.getStudents();
	}
	public Optional<Grade> getGradeForStudent(Student student) {
		return grades.stream().filter(grade -> grade.getStudent().equals(student)).findFirst();
	}
}
