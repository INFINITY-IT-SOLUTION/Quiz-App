package com.quizapp.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private String categoryName;
    private String dificulty;
    private Integer numQuestions;
    private String title;

    @Override
    public String toString() {
        return "QuizDto [categoryName=" + categoryName + ", numQuestions=" + numQuestions + ", title=" + title + "]";
    }
}