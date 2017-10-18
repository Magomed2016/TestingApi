package ru.pflb.testingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;

/**
 * Created by insider on 12.09.2017.
 */


@Entity
@Table(name = "Answer")
@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor
public class Answer implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer answerId;

    @Column(name = "text", unique = false, nullable = false)
    //@NonNull
    private String text;

    @Column(name = "isRight", unique = false, nullable = false)
    //@NonNull
    protected Boolean isRight;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "questionId", nullable = false)
    @JsonIgnore
    private Question question;

    public void update(Answer answer){
        if (answer.text != null){
            this.text = answer.text;
        }

        if (answer.isRight != null){
            this.isRight = answer.isRight;
        }
    }
}