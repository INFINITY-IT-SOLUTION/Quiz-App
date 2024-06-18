package com.quizapp.question_service.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String category;
    private String difficltyLevel;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionTitle;
    private String rightAnswer;


    @Override
    public String toString() {
        return "Question [id=" + id + ", category=" + category + ", difficltyLevel=" + difficltyLevel + ", option1="
                + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4
                + ", questionTitle=" + questionTitle + ", rightAnswer=" + rightAnswer + "]";
    }


}