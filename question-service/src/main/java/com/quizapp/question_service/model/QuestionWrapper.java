package com.quizapp.question_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuestionWrapper {
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;

    @Override
    public String toString() {
        return "QuestionWrapper [id=" + id + ", questionTitle=" + questionTitle + ", option1=" + option1 + ", option2="
                + option2 + ", option3=" + option3 + ", option4=" + option4 + ", rightAnswer=" + rightAnswer + "]";
    }


}