package org.kras.aws.awsqna.service;

import lombok.RequiredArgsConstructor;
import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.repo.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    public List<Answer> getAnswers() {
       return answerRepository.findAll();
    }
}
