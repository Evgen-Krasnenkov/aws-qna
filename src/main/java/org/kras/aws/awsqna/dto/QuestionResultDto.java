package org.kras.aws.awsqna.dto;

import java.util.List;

public class QuestionResultDto {
    private String questionText;
    private List<AnswerResultDto> answers;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<AnswerResultDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResultDto> answers) {
        this.answers = answers;
    }

    public static class AnswerResultDto {
        private String answerText;
        private boolean isCorrect;
        private boolean isSelected;

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
