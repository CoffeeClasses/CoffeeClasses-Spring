package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.NotNull;

public class CourseDeleteRequestDTO {
	@NotNull(message = "Le cours doit avoir un ID.")
	private Long id; // Course ID for deletion

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
