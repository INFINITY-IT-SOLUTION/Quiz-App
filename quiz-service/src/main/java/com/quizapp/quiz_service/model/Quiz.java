package com.quizapp.quiz_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;

    @ElementCollection
    private List<Integer>questionIds;

    @Override
    public String toString() {
        return "Quiz [id=" + id + ", title=" + title + ", questionIds=" + questionIds + "]";
    }

}