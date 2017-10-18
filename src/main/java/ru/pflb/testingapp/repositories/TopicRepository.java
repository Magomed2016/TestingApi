package ru.pflb.testingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Topic;

import java.util.Collection;

/**
 * Created by insider on 13.09.2017.
 */
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTopicId(Integer topicId);
}
