package com.quizapp.question_service.service;

import com.quizapp.question_service.exception.QuestionNotFoundException;
import com.quizapp.question_service.model.Question;
import com.quizapp.question_service.model.QuestionWrapper;
import com.quizapp.question_service.model.Response;
import com.quizapp.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    public Question addQuestion(Question question) {
        Question d=questionRepository.save(question);

        return d;
    }

    public List<Question> getQuestionsByCategory(String category)
    {
        return questionRepository.findByCategory(category);
    }

    public String updateQuestionById(Integer id,Question question)
    {
        if(questionRepository.findById(id).isPresent())
        {
            questionRepository.save(question);

            return "Question with id "+id+" has updated successfully!!";
        }
        else
        {
            throw new QuestionNotFoundException("Question with id "+id+" doesn't exist!!");
        }
    }

    public String deleteQuestionById(Integer id) {
        if(questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);

            return "Question with id "+id+" has deleted successfully!!";
        }
        else
        {
            throw new QuestionNotFoundException("Question with id "+id+" doesn't exist!!");
        }
    }

    public List<Integer> generateRandomQuestionsForQuiz(String category, Integer numQ)
    {
        return questionRepository.findRandomQuestionsByCategory(category, numQ);
    }

    public List<QuestionWrapper> getQuestionsFromId(List<Integer> questionIds)
    {
        List<Question> questions=new ArrayList<>();
        List<QuestionWrapper>wrappers=new ArrayList<>();

        for(Integer id:questionIds)
        {
            questions.add(questionRepository.findById(id).get());
        }
        for(Question question:questions) {
            QuestionWrapper wrapper=new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrapper.setRightAnswer(question.getRightAnswer());

            wrappers.add(wrapper);
        }
        return wrappers;
    }

    public Integer getScore(List<Response> responses) {
        int score=0;

        for(Response response:responses) {

            if(response.getResponse().equals(questionRepository.findById(response.getId()).get().getRightAnswer())) {
                score++;
            }
        }

        return score;
    }

    public Question getQuestionById(Integer id) {

        Question question=null;
        if(questionRepository.findById(id).isPresent())
        {
            question= questionRepository.findById(id).get();
        }
        return question;
    }

}