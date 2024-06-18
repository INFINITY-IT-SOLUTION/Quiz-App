package com.quizapp.quiz_service.feign;

import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "question-service", url = "http://localhost:5000/")
@CrossOrigin(origins="http://localhost:/")
public interface QuizInterface
{
    @GetMapping("/question/randomquestion")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ);

    @PostMapping("/question/getquestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/getscore")
    public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}