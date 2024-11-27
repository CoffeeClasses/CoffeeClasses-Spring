package fr.cyu.coffeeclasses.spring.repository;

import fr.cyu.coffeeclasses.spring.model.element.Assessment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
	Optional<Assessment> findById(Long id);
}