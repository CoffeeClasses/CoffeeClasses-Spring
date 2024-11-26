package fr.cyu.coffeeclasses.spring;

import javax.sql.DataSource;

import fr.cyu.coffeeclasses.spring.interceptor.*;
import fr.cyu.coffeeclasses.spring.model.user.Administrator;
import fr.cyu.coffeeclasses.spring.model.user.Student;
import fr.cyu.coffeeclasses.spring.model.user.Teacher;
import fr.cyu.coffeeclasses.spring.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

	/* Interceptors */
	@Bean
	public UserInterceptor userInterceptor() {
		return new UserInterceptor();
	}
	@Bean
	public RoleInterceptor teacherInterceptor() {
		return new RoleInterceptor(Teacher.class);
	}
	@Bean
	public RoleInterceptor studentInterceptor() {
		return new RoleInterceptor(Student.class);
	}
	@Bean
	public RoleInterceptor administratorInterceptor() {
		return new RoleInterceptor(Administrator.class);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// User
		registry.addInterceptor(userInterceptor()).addPathPatterns(
				"/panel",
				"/panel/*"
		);

		// Admin, Teacher, and Student
		registry.addInterceptor(teacherInterceptor()).addPathPatterns(
				"/panel/teacher",
				"/panel/teacher/*"
		);
		registry.addInterceptor(studentInterceptor()).addPathPatterns(
				"/panel/student",
				"/panel/student/*"
		);
		registry.addInterceptor(administratorInterceptor()).addPathPatterns(
				"/panel/admin",
				"/panel/admin/*"
		);
	}

	/* Workaround for a bug preventing startup */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("fr.cyu.coffeeclasses.spring.model");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		// Explicitly set the EntityManagerFactory interface to avoid conflict between
		// the EntityManagerFactory interfaces used by Spring and Hibernate.
		em.setEntityManagerFactoryInterface(EntityManagerFactory.class);

		return em;
	}
}