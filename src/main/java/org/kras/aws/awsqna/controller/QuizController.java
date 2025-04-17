package org.kras.aws.awsqna.controller;


import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.entity.Question;
import org.kras.aws.awsqna.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String showQuizPage(Model model) {
        List<Question> questions = quizService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitAnswers(@RequestParam("answers") List<Long> selectedAnswerIds, Model model) {
        int correct = 0;
        int incorrect = 0;

        for (Question question : quizService.getAllQuestions()) {
            for (Answer answer : question.getAnswers()) {
                if (selectedAnswerIds.contains(answer.getId()) && answer.getIsCorrect()) {
                    correct++;
                } else if (selectedAnswerIds.contains(answer.getId()) && !answer.getIsCorrect()) {
                    incorrect++;
                }
            }
        }

        model.addAttribute("correct", correct);
        model.addAttribute("incorrect", incorrect);
        return "result";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("questions", quizService.getAllQuestions());
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addQuestion(@ModelAttribute Question question) {
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{questionId}/answers")
    public String manageAnswers(@PathVariable Long questionId, Model model) {
        Question question = quizService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("newAnswer", new Answer());
        return "manage-answers";
    }

    @PostMapping("/admin/{questionId}/answers/add")
    public String addAnswerToQuestion(@PathVariable Long questionId, @ModelAttribute Answer newAnswer) {
        quizService.addAnswerToQuestion(questionId, newAnswer);
        return "redirect:/admin/" + questionId + "/answers";
    }
}