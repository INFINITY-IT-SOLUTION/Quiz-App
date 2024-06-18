package com.quizapp.question_service.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails
{
    private String statusCode;
    private String message;
    private LocalDateTime when;


    @Override
    public String toString() {
        return "ErrorDetails [statusCode=" + statusCode + ", message=" + message + ", when=" + when + "]";
    }

}