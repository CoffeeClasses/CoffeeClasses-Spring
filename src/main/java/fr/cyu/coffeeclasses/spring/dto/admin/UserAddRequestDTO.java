package fr.cyu.coffeeclasses.spring.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserAddRequestDTO {
	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotNull(message = "Birth date is mandatory")
	@Past(message = "Birth date must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Ensures date parsing
	private LocalDate birthDate;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is mandatory")
	private String password;

	@NotBlank(message = "Role is mandatory")
	private String role;

	// Getters and setters
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

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
