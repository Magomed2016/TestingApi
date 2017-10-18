package ru.pflb.testingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Admin;
import ru.pflb.testingapp.entities.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Statistic findByStatisticId(Integer statisticId);
    //Set<Answer> findByQuestion(Question question);
}

