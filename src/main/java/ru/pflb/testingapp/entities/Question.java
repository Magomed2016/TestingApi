package ru.pflb.testingapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by insider on 12.09.2017.
 */
@Entity
@Table(name = "Question")
@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor
public class Question implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer questionId;

    @Column(name = "questionText", unique = false, nullable = false)
    //@NonNull
    private String questionText;

    @Column(name = "questionType", unique = false, nullable = false)
    //@NonNull
    @Enumerated(EnumType.ORDINAL)
    private QuestionType questionType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    //@JsonIgnore
    private Set<Answer> answers = new HashSet<>();

    @ManyToMany(mappedBy = "questions")
    //@JsonIgnore
    private Set<Topic> topics = new HashSet<>();

    public void update(Question question){
        if (question.questionText != null){
            this.questionText = question.questionText;
        }

        if (question.questionType != null){
            this.questionType = question.questionType;
        }

        if (question.answers.size() > 0){
            this.answers = question.answers;
        }

        if (question.topics.size() > 0){
            this.topics = question.topics;
        }
    }

    public Integer countRightAnswers(){
        int i = 0;
        for (Answer answer: answers){
            if (answer.isRight) i++;
        }
        return i;
    }

    public boolean allFieldsNotEmpty(){
        if (questionText != null && questionType != null && answers.size() > 0 && topics.size() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean verifyAnswers(){
        int rightAnswersCount = countRightAnswers();
        switch (questionType){
            case RADIO:{
                if (rightAnswersCount != 1 || answers.size() <= 1){
                    return false;
                }
                break;
            }
            case CHECKBOX:{
                if (rightAnswersCount == 0 || answers.size() <= 1){
                    return false;
                }
                break;
            }
            case TEXTAREA:{
                if (rightAnswersCount != 1 || answers.size() != 1){
                    return false;
                }
                break;
            }
        }
        return true;
    }
}
