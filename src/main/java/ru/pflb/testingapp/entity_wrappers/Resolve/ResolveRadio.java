package ru.pflb.testingapp.entity_wrappers.Resolve;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.regexp.internal.RE;
import lombok.*;

@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ResolveRadio {

    private Integer questionId;
    private Integer answerId;

    public ResolveRadio(Integer questionId, Integer answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }


}
