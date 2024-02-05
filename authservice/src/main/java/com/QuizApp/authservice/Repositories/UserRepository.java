package com.QuizApp.authservice.Repositories;

import com.QuizApp.authservice.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findUserByEmail(String email);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    UserDetails findUserByUsername(String username);

    User getReferenceByEmail(String email);
}
