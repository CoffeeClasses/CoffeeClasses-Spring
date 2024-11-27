package fr.cyu.coffeeclasses.spring.repository;

import fr.cyu.coffeeclasses.spring.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);

	// Search by role and string fields (dynamic filtering)
	@Query("""
        SELECT u FROM User u 
        WHERE (:role IS NULL OR TYPE(u) = :role)
        AND (
            :search IS NULL OR 
            u.firstName LIKE %:search% OR 
            u.lastName LIKE %:search% OR 
            u.email LIKE %:search%
        )
    """)
	Set<User> searchUsersByRoleAndString(
			@Param("role") Class<? extends User> role,
			@Param("search") String search
	);
}