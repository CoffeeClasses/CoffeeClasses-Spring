package fr.cyu.coffeeclasses.spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String mail;

	@NotBlank(message = "Password is mandatory")
	private String password;

	// Getters and setters
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
