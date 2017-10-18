package ru.pflb.testingapp.entity_wrappers;

import lombok.Getter;
import ru.pflb.testingapp.entities.Question;
import ru.pflb.testingapp.entities.Topic;

import java.util.Set;

/**
 * Created by insider on 16.09.2017.
 */
@Getter
public class QuestionTextOnly {
    private final Integer questionId;
    private final String questionText;
    private final Set<Topic> topics;

    public QuestionTextOnly(Question question) {
        this.questionId = question.getQuestionId();
        this.questionText = question.getQuestionText();
        this.topics = question.getTopics();
    }
}