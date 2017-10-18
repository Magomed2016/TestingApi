package ru.pflb.testingapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by insider on 12.09.2017.
 */

@Entity
@Table(name = "Topic")
@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor
public class Topic implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topicId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer topicId;

    @Column(name = "topicName", unique = false, nullable = false)
    //@NonNull
    private String topicName;

    @ManyToMany
    @JoinTable(name = "topic_question", joinColumns = @JoinColumn(name = "topicId", referencedColumnName = "topicId"),
            inverseJoinColumns = @JoinColumn(name = "questionId", referencedColumnName = "questionId"))
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();
    public Topic(String topicName){
        this.topicName = topicName;
    }
}
