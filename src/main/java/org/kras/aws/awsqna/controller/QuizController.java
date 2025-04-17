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
    public String submitAnswers(@RequestParam(value = "answers", required = false) List<Long> selectedAnswerIds, Model model) {
        int correct = 0;
        List<Question> allQuestions = quizService.getAllQuestions();
        int incorrect = allQuestions.size();
        if (selectedAnswerIds == null || selectedAnswerIds.isEmpty()) {
            fillModel(model, correct, incorrect, incorrect);
        } else {
            for (Question question : allQuestions) {
                for (Answer answer : question.getAnswers()) {
                    if (selectedAnswerIds.contains(answer.getId()) && answer.getIsCorrect()) {
                        correct++;
                        incorrect--;
                    }
                }
            }
        }
        fillModel(model, correct, incorrect, allQuestions.size());
        return "result";
    }

    private void fillModel(final Model model, int correct, int incorrect, int allQuestionsSize) {
        model.addAttribute("correct", correct);
        model.addAttribute("incorrect", incorrect);
        model.addAttribute("grade", String.valueOf(Math.round((double) correct / allQuestionsSize * 100)));
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