package fr.cyu.coffeeclasses.spring;

import fr.cyu.coffeeclasses.spring.model.user.Administrator;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.searchUsersByRoleAndString(Administrator.class, null).isEmpty()) {
			userRepository.save(
					new Administrator(
							"Default",
							"Admin",
							"admin-coffeeclasses@yopmail.com",
							"admin123",
							LocalDate.of(2003, 10, 9)
					)
			);
			logger.info("No admin users found. Default admin account added.");
		} else {
			logger.info("Admin users were found. No initialization is required.");
		}
	}
}
