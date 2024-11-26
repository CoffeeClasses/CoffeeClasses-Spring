package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.NotNull;

public class UserDeleteRequestDTO {
	@NotNull(message = "User ID is required")
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
