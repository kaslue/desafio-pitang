package com.pitang.test.repository;

import java.util.Optional;

import com.pitang.test.models.ERole;
import com.pitang.test.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
