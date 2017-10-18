package ru.pflb.testingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Admin;
import ru.pflb.testingapp.entities.TestPerson;

public interface TestPersonRepository extends JpaRepository<TestPerson, Long> {
    TestPerson findBytestPersonId(Integer testPersonId);
    //Set<Answer> findByQuestion(Question question);
}

