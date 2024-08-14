package com.igor.user.service.service;

import com.igor.user.service.config.JwtProvider;
import com.igor.user.service.model.User;
import com.igor.user.service.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserProfile() {
        // Récupérer la requête HTTP actuelle
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // Extraire le token JWT de l'en-tête Authorization
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;

        if (jwt == null) {
            throw new RuntimeException("JWT token is missing or invalid.");
        }

        // Extraire l'email du token JWT
        String email = JwtProvider.getEmailFromJwtToken(jwt);

        // Récupérer et retourner le profil utilisateur à partir de l'email
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
