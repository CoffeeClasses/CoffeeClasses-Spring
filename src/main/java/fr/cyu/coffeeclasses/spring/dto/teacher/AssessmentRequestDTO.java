package fr.cyu.coffeeclasses.spring.dto.teacher;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class AssessmentRequestDTO {
	@NotNull(message = "Le cours doit être spécifié.")
	private Long selectedCourse; // ID of the selected course

	@NotNull(message = "Le nom de l'évaluation doit être spécifié.")
	@Size(min = 1, max = 100, message = "Le nom de l'évaluation doit être entre 1 et 100 caractères.")
	private String name; // Name of the assessment

	@NotNull(message = "La note maximale doit être spécifiée.")
	@Positive(message = "La note maximale doit être un nombre positif.")
	private Double maxGrade; // Maximum grade for the assessment

	@NotNull(message = "La date de l'évaluation doit être spécifiée.")
	@PastOrPresent(message = "La date de l'évaluation ne peut pas être dans le futur.")
	private LocalDate date; // Date of the assessment

	// Getters and Setters
	public Long getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(Long selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getMaxGrade() {
		return maxGrade;
	}
	public void setMaxGrade(Double maxGrade) {
		this.maxGrade = maxGrade;
	}

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
