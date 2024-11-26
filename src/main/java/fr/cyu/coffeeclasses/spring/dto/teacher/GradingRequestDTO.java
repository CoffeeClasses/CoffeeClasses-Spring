package fr.cyu.coffeeclasses.spring.dto.teacher;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class GradingRequestDTO {
	@NotNull(message = "L'évaluation doit être spécifiée.")
	private Long assessmentId; // ID of the assessment

	private Map<Long, Double> grades; // Map of student ID to grade value

	// Getters and Setters
	public Long getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public Map<Long, Double> getGrades() {
		return grades;
	}
	public void setGrades(Map<Long, Double> grades) {
		this.grades = grades;
	}
}
