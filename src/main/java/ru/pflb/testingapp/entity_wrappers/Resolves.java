package ru.pflb.testingapp.entity_wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.regexp.internal.RE;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class Resolves {
    private Integer personId;
    private Integer topicId;
    private String resolves;

}
