package org.kras.aws.awsqna.service;


import lombok.RequiredArgsConstructor;
import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.entity.Question;
import org.springframework.stereotype.Component;
import org.kras.aws.awsqna.repo.QuestionRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }


    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }


    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }


    public void addAnswerToQuestion(Long questionId, Answer newAnswer) {
        Question question = getQuestionById(questionId);
        newAnswer.setQuestion(question);
        question.getAnswers().add(newAnswer);
        questionRepository.save(question);
    }
}