package com.quizapp.question_service.advice;

import com.quizapp.question_service.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class QuestionErrorController {
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleExceptionQuestion(QuestionNotFoundException e)
    {
        ErrorDetails error=new ErrorDetails("404 not found",e.getMessage(), LocalDateTime.now());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleExceptionGlobally(Exception e)
    {
        ErrorDetails error=new ErrorDetails("404 not found",e.getMessage(),LocalDateTime.now());
        return new ResponseEntity(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
