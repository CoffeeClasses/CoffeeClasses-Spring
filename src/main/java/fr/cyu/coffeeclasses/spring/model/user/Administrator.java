package fr.cyu.coffeeclasses.spring.model.user;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Administrator extends User {
	protected Administrator() {}
	public Administrator(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		super(firstName, lastName, email, password, birthDate);
	}
}