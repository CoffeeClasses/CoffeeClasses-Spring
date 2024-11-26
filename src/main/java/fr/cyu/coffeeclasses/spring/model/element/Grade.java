package fr.cyu.coffeeclasses.spring.model.element;

import fr.cyu.coffeeclasses.spring.model.user.Student;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "grades",  uniqueConstraints = {
	// We prevent 2 grades with the same assignment and student from coexisting.
	@UniqueConstraint(columnNames = {"assessment_id", "student_id"})
})
public class Grade {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	// For what assessment ?
	@ManyToOne(optional = false)
	private Assessment assessment;

	// Which enrollment (student + course)?
	@ManyToOne(optional = false)
	private Enrollment enrollment;

	// What did they get ?
	@Column(nullable = false)
	private double value;

	// When did they get it ?
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime date;

	/*
		Methods
	 */
	protected Grade() {}
	public Grade(Assessment assessment, Enrollment enrollment, double value) {
		setAssessment(assessment);
		setEnrollment(enrollment);
		setValue(value);
	}

	// ID
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}

	// Assessment
	public Assessment getAssessment() {
		return assessment;
	}
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	// Enrollment (student + course)
	public Enrollment getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	// Value
	public double getValue() {
		return value;
	}
	public void setValue(double value) throws IllegalArgumentException {
		if (value > 0 && value <= assessment.getMaximum()) {
			this.value = value;
		} else {
			throw new IllegalArgumentException("Value must be between 0 and " + assessment.getMaximum());
		}
	}

	// Date
	public LocalDateTime getDate() {
		return date;
	}
	private void setDate(LocalDateTime date) {
		this.date = date;
	}

	/*
		Bonus
	 */
	public Student getStudent() {
		return enrollment.getStudent();
	}
}
