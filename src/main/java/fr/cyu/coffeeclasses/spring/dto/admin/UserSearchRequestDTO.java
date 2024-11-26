package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.Pattern;

public class UserSearchRequestDTO {
	private String role;
	@Pattern(regexp = "^[a-zA-Z0-9.@\\s]*$", message = "Impossible de rechercher cela.")
	private String search;

	// Getters and Setters
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
