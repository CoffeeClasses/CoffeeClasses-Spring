package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseAddRequestDTO {
	@NotBlank(message = "Le nom du cours ne peut pas être vide.")
	private String name; // Course name

	@NotNull(message = "Le professeur doit être sélectionné.")
	private Long teacherId; // ID of the teacher assigned to the course

	// Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
}
