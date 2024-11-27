package fr.cyu.coffeeclasses.spring.repository;

import fr.cyu.coffeeclasses.spring.model.element.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findById(Long id);

	@Query("SELECT c FROM Course c " +
			"WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
			"OR LOWER(c.teacher.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
			"OR LOWER(c.teacher.lastName) LIKE LOWER(CONCAT('%', :search, '%'))")
	Set<Course> searchCourses(String search);
}