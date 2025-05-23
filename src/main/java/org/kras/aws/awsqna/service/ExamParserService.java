package org.kras.aws.awsqna.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kras.aws.awsqna.config.QuestionsProperties;
import org.kras.aws.awsqna.dto.ExamQuestionDto;
import org.kras.aws.awsqna.entity.Question;
import org.kras.aws.awsqna.mapper.ExamMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExamParserService {
    private final QuestionService questionService;
    private final ExamMapper examMapper;
    private final QuestionsProperties questionsProperties;
    private final ObjectMapper mapper;

    @PostConstruct
    public void parseExamFile() throws IOException {
        for (String fileName : questionsProperties.getList()) {
            InputStream inputStream = getResource(fileName);
            List<ExamQuestionDto> examQuestionDtos = mapper.readValue(inputStream, new TypeReference<>() {});
            List<Question> questions = mapToQuestion(examQuestionDtos.parallelStream());
            List<Question> savedQuestions = questionService.persistAll(questions);
        }
    }

    private InputStream getResource(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        InputStream inputStream = null;
        try {
            return classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("Error loading resource: {}", fileName, e);
        }
        return inputStream;
    }

    private List<Question> mapToQuestion(Stream<ExamQuestionDto> stream) {
        return stream
                .map(examMapper::mapQuestion)
                .collect(Collectors.toList());
    }
}
