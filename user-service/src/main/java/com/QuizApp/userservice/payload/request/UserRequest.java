package com.QuizApp.userservice.payload.request;

import lombok.Builder;
import lombok.Data;


@Builder
public class UserRequest {
    private String userId;
}
