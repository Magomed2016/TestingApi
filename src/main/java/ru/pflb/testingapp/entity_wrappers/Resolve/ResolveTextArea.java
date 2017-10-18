package ru.pflb.testingapp.entity_wrappers.Resolve;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.regexp.internal.RE;
import lombok.*;

@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ResolveTextArea {
    private Integer questionId;
    private String answer;
    private Integer answerId;

    public ResolveTextArea(Integer questionId, String answer, Integer answerId) {
        this.questionId = questionId;
        this.answer = answer;
        this.answerId = answerId;
    }
}
