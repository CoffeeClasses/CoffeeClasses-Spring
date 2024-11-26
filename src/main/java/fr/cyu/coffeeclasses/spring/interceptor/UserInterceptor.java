package fr.cyu.coffeeclasses.spring.interceptor;

import fr.cyu.coffeeclasses.spring.model.user.User;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		Object userId = request.getSession().getAttribute("userId");

		if (userId == null) {
			response.sendRedirect("/login");
			return false;
		} else {
			try {
				Optional<User> userOpt = userRepository.findById(Long.parseLong(userId.toString()));

				if (userOpt.isPresent()) {
					request.setAttribute("user", userOpt.get());
					return true;
				} else {
					response.sendRedirect("/login");
					return false;
				}
			} catch (NumberFormatException e) {
				response.sendRedirect("/login");
				return false;
			}
		}
	}
}
