package com.QuizApp.authservice.Repositories;

import com.QuizApp.authservice.Domains.PasswordResetToken;
import com.QuizApp.authservice.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Boolean existsByUser(User user);

}
