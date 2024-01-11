package com.QuizApp.userservice.service.impl;

import com.QuizApp.userservice.exception.UserServiceCustomException;
import com.QuizApp.userservice.entity.User;
import com.QuizApp.userservice.payload.response.UserResponse;
import com.QuizApp.userservice.repository.UserRepository;
import com.QuizApp.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserById(long userId) {

        log.info("UserServiceImpl | getUserById is called");
        log.info("UserServiceImpl | getUserById | Get the user for userId: {}", userId);

        User user
                = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserServiceCustomException("user with given Id not found","USER_NOT_FOUND"));

        UserResponse userResponse
                = new UserResponse();

        copyProperties(user, userResponse);

        log.info("UserServiceImpl | getUserById | userResponse :" + userResponse.toString());

        return userResponse;
    }

    @Override
    public void deleteUserById(long userId) {
        log.info("User id: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.info("Im in this loop {}", !userRepository.existsById(userId));
            throw new UserServiceCustomException(
                    "User with given with Id: " + userId + " not found:",
                    "USER_NOT_FOUND");
        }
        log.info("Deleting USER with id: {}", userId);
        userRepository.deleteById(userId);

    }
}
