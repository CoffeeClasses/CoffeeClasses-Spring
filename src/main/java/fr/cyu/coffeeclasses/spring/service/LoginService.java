package fr.cyu.coffeeclasses.spring.service;

import fr.cyu.coffeeclasses.spring.dto.LoginRequestDTO;
import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class LoginService {
	@Autowired
	private UserRepository userRepository;

	public Optional<User> authenticate(LoginRequestDTO login) {
		Optional<User> userOpt = userRepository.findByEmail(login.getMail());
		if (userOpt.isPresent() && userOpt.get().checkPassword(login.getPassword())) {
			return userOpt;
		} else {
			return Optional.empty();
		}
	}
}
