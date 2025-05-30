package org.kras.aws.awsqna.controller;


import lombok.RequiredArgsConstructor;
import org.kras.aws.awsqna.dto.QuestionAnswersForm;
import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.entity.Question;
import org.kras.aws.awsqna.service.AnswerService;
import org.kras.aws.awsqna.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

import static org.kras.aws.awsqna.service.QuizService.SIZE;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final AnswerService answerService;

    @GetMapping("/")
    public String showQuizPage(Model model) {
        List<Question> questions = quizService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitAnswers(@ModelAttribute QuestionAnswersForm questionAnswersForm, Model model) {
        int correct = 0;
        Map<String, List<String>> answers = questionAnswersForm.getAnswers();
        for (String questionId : answers.keySet()) {
            List<Long> correctAnswerIds = quizService.getQuestionById(Long.parseLong(questionId))
                    .getAnswers().stream()
                    .filter(Answer::getIsCorrect)
                    .map(Answer::getId)
                    .toList();

            List<Long> selectedAnswerIds = answers.get(questionId).stream()
                    .map(Long::parseLong)
                    .toList();
            if (correctAnswerIds.equals(selectedAnswerIds)) {
                correct++;
            }
        }
        fillModel(model, correct);
        return "result";
    }

    private void fillModel(final Model model, int correct) {
        model.addAttribute("correct", correct);
        model.addAttribute("incorrect", SIZE - correct);
        model.addAttribute("grade", String.valueOf(Math.round((double) correct / SIZE * 100)));
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