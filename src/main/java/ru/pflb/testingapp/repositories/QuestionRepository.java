package ru.pflb.testingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Question;

import java.util.Collection;

/**
 * Created by insider on 13.09.2017.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByQuestionId(Integer questionId);
}
