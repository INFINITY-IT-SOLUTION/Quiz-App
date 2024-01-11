package com.QuizApp.userservice.service;

import com.QuizApp.userservice.payload.response.UserResponse;

public interface UserService {

    UserResponse getUserById(long userId);


    public void deleteUserById(long userId);
}
