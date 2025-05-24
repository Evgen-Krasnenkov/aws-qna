package org.kras.aws.awsqna.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kras.aws.awsqna.entity.Question;
import org.kras.aws.awsqna.repo.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;

    @PreDestroy
    public void cleanUp() {
        log.info("Cleaning up resources...");
        questionRepository.deleteAll();
    }

    public Optional<Question> persistsQuestion(Question question) {
        return Optional.of(questionRepository.save(question));
    }

    public List<Question> persistAll(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }
}
