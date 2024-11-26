package fr.cyu.coffeeclasses.spring.model.element;

import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String name;

	// Which teacher is assigned to it ?
	@ManyToOne(optional = false)
	private Teacher teacher;

	// Any assessments in this course ?
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Assessment> assessments = new HashSet<>();

	// Enrollments for this course
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Enrollment> enrollments = new HashSet<>();

	/*
		Methods
	 */
	protected Course() {}
	public Course(String name, Teacher teacher) {
		setName(name);
		setTeacher(teacher);
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

	// Teacher
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	// Assessments
	public Set<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}
	public void addAssessment(String name, LocalDate date, int maximum) {
		Assessment assessment = new Assessment(name, date, maximum, this);
		assessments.add(assessment);
	}
	public void removeAssessment(Assessment assessment) {
		assessments.remove(assessment);
	}

	// Enrollments
	public Set<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void addStudent(Student student) {
		enrollments.add(new Enrollment(student, this));
	}
	public void removeEnrollment(Enrollment enrollment) {
		enrollments.remove(enrollment);
		enrollment.setCourse(null);
	}

	/* Bonus */
	public Set<Student> getStudents() {
		Set<Student> students = new HashSet<>();
		for (Enrollment enrollment : enrollments) {
			students.add(enrollment.getStudent());
		}
		return students;
	}
	public Optional<Enrollment> getEnrollmentForStudent(Student student) {
		return enrollments.stream().filter(enrollment -> enrollment.getStudent().equals(student)).findFirst();
	}

	/* Workaround for a bug */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Course course = (Course) obj;
		return id.equals(course.id);
	}
}
