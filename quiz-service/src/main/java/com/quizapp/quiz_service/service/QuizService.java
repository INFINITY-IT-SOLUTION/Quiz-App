package com.quizapp.quiz_service.service;

import com.quizapp.quiz_service.exception.QuizNotfoundException;
import com.quizapp.quiz_service.feign.QuizInterface;
import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.Quiz;
import com.quizapp.quiz_service.model.Response;
import com.quizapp.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;



    public List<Quiz> getAllQuizzes() {

        return quizRepository.findAll();
    }

    public String createQuiz(String category,Integer numQ,String title) {
        List<Integer> questions= quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);

        quizRepository.save(quiz);

        return "Quiz created successfully with id "+quiz.getId();
    }

    public List<QuestionWrapper> getQuizQuestions(Integer id) {
        Optional<Quiz> q = quizRepository.findById(id);

        if(q.isPresent())
        {
            Quiz quiz = q.get();
            List<Integer> questionIds=quiz.getQuestionIds();
            List<QuestionWrapper> questions = quizInterface.getQuestionsFromId(questionIds).getBody();


            return questions;
        }
        else
        {
            throw new QuizNotfoundException("Quiz with id "+id+" not exist!!");
        }

    }

    public Integer calculateScore(Integer id, List<Response> responses)
    {
        if(quizRepository.findById(id).isPresent())
        {
            Quiz quiz = quizRepository.findById(id).get();

            return quizInterface.getQuizScore(responses).getBody();
        }
        else
        {
            throw new QuizNotfoundException("Quiz with id "+id+" not exist!!");
        }

    }

    public String deleteQuizById(Integer id)
    {
        if(quizRepository.findById(id).isPresent())
        {
            quizRepository.deleteById(id);

            return "Quiz with id "+id+" has deleted successfully!!";
        }
        else
        {
            throw new QuizNotfoundException("Quiz with id "+id+" doesn't exist!!");
        }
    }
}