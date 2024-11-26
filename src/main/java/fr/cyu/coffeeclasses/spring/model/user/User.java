package fr.cyu.coffeeclasses.spring.model.user;

import jakarta.persistence.*;
import com.password4j.Password;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
	/*
		Fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDate birthDate;

	/*
		Methods
	 */
	protected User() {}

	protected User(String firstName, String lastName, String email, String password, LocalDate birthDate) {
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setBirthDate(birthDate);
	}

	// ID
	public Long getId() {
		return id;
	}
	private void setId(Long id) {
		this.id = id;
	}

	// First Name
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) throws IllegalArgumentException {
		if (firstName == null || firstName.isEmpty()) throw new IllegalArgumentException("First name cannot be empty");
		this.firstName = firstName;
	}

	// Last Name
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty()) throw new IllegalArgumentException("Last name cannot be empty");
		this.lastName = lastName;
	}

	// Email
	private static final String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email cannot be empty");
		// Also check formatting
		if (!email.matches(emailRegex)) {
			throw new IllegalArgumentException("Invalid email format");
		}
		this.email = email;
	}

	// Password
	public Boolean checkPassword(String password) {
		if (Password.check(password, this.password).withArgon2()) {
			return true;
		} else {
			// Legacy plain text passwords
			if (!password.startsWith("$argon2") && password.equals(this.password)) {
				// We hash it while we're at it.
				setPassword(password);
				return true;
			} else {
				return false;
			}
		}
	}
	public void setPassword(String password) {
		if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password cannot be empty");
		this.password = Password.hash(password).addRandomSalt().withArgon2().getResult();
	}

	// Birth Date
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		if (birthDate == null) throw new IllegalArgumentException("Birth date cannot be null");
		this.birthDate = birthDate;
	}

	/* Overrides */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		User user = (User) obj;
		return id.equals(user.id);
	}
}