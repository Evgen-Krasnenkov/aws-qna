package org.kras.aws.awsqna.dto;

import java.util.List;
import java.util.Map;

public class QuestionAnswersForm {
    private Map<String, List<String>> answers;

    public Map<String, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, List<String>> answers) {
        this.answers = answers;
    }
}