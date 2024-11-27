package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.Pattern;

public class CourseSearchRequestDTO {
	@Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Le nom du cours contient des caractères invalides.")
	private String search; // Search query for the course name

	// Getters and Setters
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
