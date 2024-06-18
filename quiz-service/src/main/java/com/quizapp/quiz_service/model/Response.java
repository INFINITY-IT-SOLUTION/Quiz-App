package com.quizapp.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {
    private Integer id;
    private  String response;


    @Override
    public String toString() {
        return "Response [id=" + id + ", response=" + response + "]";
    }


}