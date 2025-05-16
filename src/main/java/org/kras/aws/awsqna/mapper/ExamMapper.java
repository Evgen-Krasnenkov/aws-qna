package org.kras.aws.awsqna.mapper;


import org.kras.aws.awsqna.dto.ExamQuestionDto;
import org.kras.aws.awsqna.entity.Answer;
import org.kras.aws.awsqna.entity.Question;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questionId", source = "questionId")
    @Mapping(target = "questionText", source = "description")
    @Mapping(target = "answers", source = "choices")
    @Mapping(target = "questionHint", source = "hint")
    Question mapQuestion(ExamQuestionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "answerText", source = "value")
    @Mapping(target = "isCorrect", source = "isCorrect")
    Answer toAnswer(ExamQuestionDto.Choice choice);

    List<Answer> toAnswerList(List<ExamQuestionDto.Choice> choices);

    @AfterMapping
    default void linkAnswersToQuestion(@MappingTarget Question question, ExamQuestionDto dto) {
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            answer.setQuestion(question);
        }
    }

}
