package com.quizapp.quiz_service.controller;

import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.Quiz;
import com.quizapp.quiz_service.model.QuizDto;
import com.quizapp.quiz_service.model.Response;
import com.quizapp.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@Tag(name = "QuizService", description = "Cette API permet de jouer au quiz")
@CrossOrigin(origins = "http://localhost:5004")
public class QuizController {

    private final QuizService service;

    @Autowired
    public QuizController(QuizService service) {
        this.service = service;
    }

    @Operation(summary = "Créer un quiz", description = "Cette API accepte un objet JSON QuizDto pour créer un quiz")
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        String response = service.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer les questions d'un quiz", description = "Cette API accepte l'ID d'un quiz et renvoie une liste de questions")
    @GetMapping("/fetchquiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        List<QuestionWrapper> questions = service.getQuizQuestions(id);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer tous les quiz", description = "Cette API renvoie une liste de tous les quiz")
    @GetMapping("/allquiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = service.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @Operation(summary = "Soumettre les réponses d'un quiz", description = "Cette API accepte une liste de réponses et l'ID du quiz pour calculer le score")
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        int score = service.calculateScore(id, responses);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @Operation(summary = "Supprimer un quiz", description = "Cette API accepte l'ID d'un quiz pour le supprimer")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id) {
        String response = service.deleteQuizById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
