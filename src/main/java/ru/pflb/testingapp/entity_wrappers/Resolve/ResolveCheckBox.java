package ru.pflb.testingapp.entity_wrappers.Resolve;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.regexp.internal.RE;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
//@RequiredArgsConstructor
public class ResolveCheckBox {
    private Integer questionId;
    //private Integer answerId;
   // Set<Integer>setAnswersId;
    HashMap<String,Integer>mapAnswersId;

    public ResolveCheckBox(Integer questionId, HashMap<String,Integer>mapAnswersId) {
        this.questionId = questionId;
        this.mapAnswersId = mapAnswersId;
    }
}
