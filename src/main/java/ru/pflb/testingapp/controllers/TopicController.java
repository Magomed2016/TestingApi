package ru.pflb.testingapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.pflb.testingapp.entities.Question;
import ru.pflb.testingapp.entities.Topic;
import ru.pflb.testingapp.repositories.TopicRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by insider on 13.09.2017.
 */
@RestController
@Transactional
@RequestMapping(value = "/api/editor/topics")
public class TopicController {
    private final TopicRepository topicRepository;
    private final QuestionController questionController;

    //
    @Autowired
    public TopicController(TopicRepository topicRepository, QuestionController questionController) {
        this.topicRepository = topicRepository;
        this.questionController = questionController;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping
    public ResponseEntity<List<Topic>> getTopics(){
        List<Topic> topics = topicRepository.findAll();
        return ResponseEntity.ok(topics);
    }

    @PostMapping
    public ResponseEntity<Integer> addTopic(@RequestBody String topicName){
        Topic topic = new Topic(topicName);
        topicRepository.save(topic);
        return ResponseEntity.ok(topic.getTopicId());
    }

    @PutMapping(value = "/{topicId}")
    public ResponseEntity<?> updateTopic(@RequestBody String topicName, @PathVariable Integer topicId){
        Topic topic = topicRepository.findByTopicId(topicId);
        if (topic != null){
            topic = topicRepository.findByTopicId(topicId);
            topic.setTopicName(topicName);
            topicRepository.save(topic);
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping(value = "/{topicId}")
    public ResponseEntity<Topic> getTopic(@PathVariable Integer topicId){
        Topic topic = topicRepository.findByTopicId(topicId);
        if(topic != null){
            return ResponseEntity.ok(topic);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer topicId){
        Topic topic = topicRepository.findByTopicId(topicId);
        Set<Question> questions;
        if (topic != null){
            //Удаляем тему из всех вопросов
            questions = topic.getQuestions();
            for (Question question: questions){
                question.getTopics().remove(topic);
            }

            topicRepository.delete(topic);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
