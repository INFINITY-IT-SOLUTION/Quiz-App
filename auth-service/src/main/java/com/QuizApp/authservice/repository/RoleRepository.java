package com.QuizApp.authservice.repository;

import com.QuizApp.authservice.model.ERole;
import com.QuizApp.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
