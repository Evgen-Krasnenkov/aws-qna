package org.kras.aws.awsqna.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.entity.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "questions.load", havingValue = "true")
public class CsvService {

    private final QuestionService questionService;

    @PostConstruct
    public void loadCsvFromResources() {
        try {
            ClassPathResource resource = new ClassPathResource("questions.csv");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split("\\|");

                    Question question = new Question();
                    question.setQuestionText(columns[0]);
                    List<Answer> answers = new ArrayList<>();
                    for (int i = 1; i < columns.length; i += 2) {
                        String answerText = columns[i];
                        boolean isCorrect = Boolean.parseBoolean(columns[i + 1]);

                        Answer answer = new Answer();
                        answer.setAnswerText(answerText);
                        answer.setIsCorrect(isCorrect);
                        answer.setQuestion(question);

                        answers.add(answer);
                    }

                    question.setAnswers(answers);
                    questionService.persistsQuestion(question);
                }
            }
        } catch (Exception e) {
            log.info("Failed to process the CSV file from resources folder");
            throw new RuntimeException("Failed to process the CSV file from resources folder");
        }
    }
}