package fr.cyu.coffeeclasses.spring.repository;

import fr.cyu.coffeeclasses.spring.model.element.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findById(Long id);
}