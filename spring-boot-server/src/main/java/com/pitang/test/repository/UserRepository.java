package com.pitang.test.repository;

import java.util.Optional;

import com.pitang.test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);

	Optional<User> findByLogin(String login);

	Boolean existsByLogin(String login);

	Boolean existsByEmail(String email);
}
