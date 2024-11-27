package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseEditRequestDTO {
	@NotNull(message = "Le cours doit avoir un ID.")
	private Long id; // Course ID

	@NotNull(message = "Le nom du cours est requis.")
	@Size(min = 3, max = 100, message = "Le nom du cours doit contenir entre 3 et 100 caractères.")
	private String name; // Course name

	@NotNull(message = "L'enseignant est requis.")
	private Long teacher; // Selected teacher's ID

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getTeacher() {
		return teacher;
	}
	public void setTeacher(Long teacher) {
		this.teacher = teacher;
	}
}
