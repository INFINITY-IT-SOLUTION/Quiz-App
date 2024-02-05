package com.QuizApp.authservice.Repositories;

import com.QuizApp.authservice.Domains.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
