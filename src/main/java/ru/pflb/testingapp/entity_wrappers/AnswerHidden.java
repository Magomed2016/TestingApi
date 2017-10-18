package ru.pflb.testingapp.entity_wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.pflb.testingapp.entities.Answer;

import javax.persistence.Column;


/**
 * Created by insider on 14.09.2017.
 */
@Getter
public class AnswerHidden{
    private final Integer answerId;
    private final String text;

    public AnswerHidden(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.text = answer.getText();
    }
}
