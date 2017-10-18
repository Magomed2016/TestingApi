package ru.pflb.testingapp.entity_wrappers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.pflb.testingapp.entities.Answer;
import ru.pflb.testingapp.entities.Question;
import ru.pflb.testingapp.entities.QuestionType;
import ru.pflb.testingapp.entities.Topic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by insider on 15.09.2017.
 */
@Getter
public class QuestionFull {
    private final Integer questionId;
    private final String questionText;
    private final QuestionType questionType;
    private final Set<AnswerHidden> answers = new HashSet<>();


    public QuestionFull(Question question){
        this.questionId = question.getQuestionId();
        this.questionText = question.getQuestionText();
        this.questionType = question.getQuestionType();
        for(Answer answer: question.getAnswers()){
            answers.add(new AnswerHidden(answer));
        }
    }
}
