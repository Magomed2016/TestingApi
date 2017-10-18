package ru.pflb.testingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Answer;
import ru.pflb.testingapp.entities.Question;

import java.util.Collection;
import java.util.Set;

/**
 * Created by insider on 13.09.2017.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findByAnswerId(Integer answerId);
    Set<Answer> findByQuestion(Question question);
    //Answer findByTextAnswer(String text);
}
