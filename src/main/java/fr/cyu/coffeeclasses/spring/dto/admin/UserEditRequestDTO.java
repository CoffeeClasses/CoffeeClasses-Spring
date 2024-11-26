package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class UserEditRequestDTO {
	@NotNull(message = "User ID is required")
	private Long id;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@NotNull(message = "Birth date is required")
	@Past(message = "Birth date must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Ensures date parsing
	private LocalDate birthDate;

	@Email(message = "Valid email is required")
	@NotBlank(message = "Email is required")
	private String email;

	private String password; // Optional, only updated if provided

	private List<Long> courses; // List of selected course IDs

	// Getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Long> getCourses() {
		return courses;
	}
	public void setCourses(List<Long> courses) {
		this.courses = courses;
	}
}
