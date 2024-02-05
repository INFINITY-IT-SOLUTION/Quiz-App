package com.QuizApp.authservice.Repositories;

import com.QuizApp.authservice.Domains.User;
import com.QuizApp.authservice.Domains.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
