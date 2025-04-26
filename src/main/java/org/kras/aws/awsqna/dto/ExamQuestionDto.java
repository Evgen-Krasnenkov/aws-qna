package org.kras.aws.awsqna.dto;

import java.util.List;

public class ExamQuestionDto {
    public long answerId;
    public String question;
    public String questionClass;
    public String hint;
    public List<Choice> choices;
    public int idx;
    public String type;
    public List<UserAnswer> userAnswers;
    public Object exact;
    public boolean isFinalScore;
    public Media media;
    public Object score;
    public long questionId;
    public String description;
    public String language;
    public Object file;
    public List<Object> noMatchResponses;
    public Object answerReview;
    public List<Object> answerFeedbacks;
    public Topic topic;

    public static class Choice {
        public long id;
        public String value;
        public boolean isCorrect;
        public boolean isAdvancedEditor;
        public String language;
        public List<Object> responses;
    }

    public static class UserAnswer {
        public boolean isCorrect;
        public long choiceId;
        public long questionId;
        public Object response;
        public Object answerFeedback;
    }

    public static class Media {
        public String audioUrl;
        public String pictureUrl;
        public String videoUrl;
    }

    public static class Topic {
        public long id;
        public String name;
        public String skillId;
        public long categoryId;
    }
}
