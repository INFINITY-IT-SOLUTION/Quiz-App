package com.quizapp.question_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private Integer id;
    private  String response;

    public Response() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Response(Integer id, String response) {
        super();
        this.id = id;
        this.response = response;
    }

    @Override
    public String toString() {
        return "Response [id=" + id + ", response=" + response + "]";
    }


}