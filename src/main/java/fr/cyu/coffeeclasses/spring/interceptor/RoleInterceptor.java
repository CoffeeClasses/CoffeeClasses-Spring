package fr.cyu.coffeeclasses.spring.interceptor;

import fr.cyu.coffeeclasses.spring.model.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {
	// Time for a trick
	private final Class<?> requiredRole;
	public RoleInterceptor(Class<?> requiredRole) {
		this.requiredRole = requiredRole;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getAttribute("user");
		if (!requiredRole.isInstance(user)) {
			response.sendRedirect("/panel/home");
			return false;
		}
		return true;
	}
}
