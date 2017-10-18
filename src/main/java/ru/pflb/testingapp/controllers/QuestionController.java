package ru.pflb.testingapp.controllers;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.pflb.testingapp.entities.Answer;
import ru.pflb.testingapp.entities.Question;
import ru.pflb.testingapp.entities.QuestionType;
import ru.pflb.testingapp.entities.Topic;
import ru.pflb.testingapp.entity_wrappers.QuestionTextOnly;
import ru.pflb.testingapp.repositories.AnswerRepository;
import ru.pflb.testingapp.repositories.QuestionRepository;
import ru.pflb.testingapp.repositories.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by insider on 13.09.2017.
 */
@RestController
@Transactional
@RequestMapping("/api/editor/questions")
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, AnswerRepository answerRepository, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.topicRepository = topicRepository;
    }


    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping
    ResponseEntity<List<QuestionTextOnly>> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionTextOnly> questionTextOnlyList = new ArrayList<>();
        for (Question question : questions) {
            questionTextOnlyList.add(new QuestionTextOnly(question));
        }
        return ResponseEntity.ok(questionTextOnlyList);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping(value = "/{questionId}")
    ResponseEntity<Question> getQuestion(@PathVariable Integer questionId) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question != null) {
            return ResponseEntity.ok(question);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<Integer> addQuestion(@RequestBody Question question) {
        Set<Answer> answers = question.getAnswers();
        if (!question.allFieldsNotEmpty()) return ResponseEntity.badRequest().build();

        question.setAnswers(null);///?зачем
        questionRepository.save(question);
        for (Answer answer : answers) {
            answer.setQuestion(question);
        }
        question.setAnswers(answers);
        answerRepository.save(answers);
        for (Topic topic : question.getTopics()) {
            Topic topicRep = topicRepository.findByTopicId(topic.getTopicId());
            topicRep.getQuestions().add(question);
            topicRepository.save(topicRep);
        }
        if (!question.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(question.getQuestionId());
    }

    @PutMapping(value = "/{questionId}")
    ResponseEntity<?> updateQuestion(@RequestBody Question question, @PathVariable Integer questionId) {
        Question questionOld = questionRepository.findByQuestionId(questionId);
        Set<Answer> answers;
        if (questionOld == null) return ResponseEntity.notFound().build();

        //Обновляем ответы
        if (question.getAnswers().size() > 0) {
            //У вопроса типа TEXTAREA может быть лишь один вариант ответа
            if (question.getQuestionType() != null && question.getQuestionType() == QuestionType.TEXTAREA
                    && question.getAnswers().size() > 1) {
                return ResponseEntity.badRequest().build();
            }
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(questionOld);
            }
            answerRepository.delete(questionOld.getAnswers());
            answerRepository.save(question.getAnswers());
        }
        //Обновляем темы
        if (question.getTopics().size() > 0) {
            for (Topic topic : questionOld.getTopics()) {
                Topic topicRep = topicRepository.findByTopicId(topic.getTopicId());
                topicRep.getQuestions().remove(questionOld);
                topicRepository.save(topicRep);
            }
            for (Topic topic : question.getTopics()) {
                Topic topicRep = topicRepository.findByTopicId(topic.getTopicId());
                topicRep.getQuestions().add(questionOld);
                topicRepository.save(topicRep);
            }
        }

        questionOld.update(question);
        questionRepository.save(questionOld);

        if (!questionOld.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{questionId}")
    ResponseEntity<?> deleteQuestion(@PathVariable Integer questionId) {
        Question question = questionRepository.findByQuestionId(questionId);
        Set<Answer> answers;
        Set<Topic> topics;
        if (question != null) {
            answers = answerRepository.findByQuestion(question);
            if (answers.size() > 0) {
                answerRepository.delete(answers);
            }
            //Удаляем вопрос из всех тем
            topics = question.getTopics();
            for (Topic topic : topics) {
                topic.getQuestions().remove(question);
                topicRepository.save(topic);
            }

            questionRepository.delete(question);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping(value = "/{questionId}/topics")
    ResponseEntity<Set<Topic>> getTopics(@PathVariable Integer questionId) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(question.getTopics());
    }

    @PutMapping(value = "/{questionId}/topics/{topicId}")
    ResponseEntity<?> putTopic(@PathVariable Integer questionId, @PathVariable Integer topicId) {
        Topic topic = topicRepository.findByTopicId(topicId);
        Question question;
        if (topic == null) return ResponseEntity.badRequest().build();
        question = questionRepository.findByQuestionId(questionId);
        if (question == null) return ResponseEntity.badRequest().build();

        Set<Topic> topics = question.getTopics();
        if (!topics.contains(topic)) {
            topics.add(topic);
            topic.getQuestions().add(question);
        }
        questionRepository.save(question);
        topicRepository.save(topic);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{questionId}/topics/{topicId}")
    ResponseEntity<?> deleteTopic(@PathVariable Integer questionId, @PathVariable Integer topicId) {
        Topic topic = topicRepository.findByTopicId(topicId);
        Question question;
        if (topic == null) return ResponseEntity.badRequest().build();
        question = questionRepository.findByQuestionId(questionId);
        if (question == null) return ResponseEntity.badRequest().build();
        topic.getQuestions().remove(question);
        Set<Topic> topics = question.getTopics();
        if (!topics.contains(topic)) ResponseEntity.badRequest().build();
        topics.remove(topic);
        questionRepository.save(question);
        topicRepository.save(topic);
        return ResponseEntity.ok().build();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping(value = "/{questionId}/answers")
    ResponseEntity<Set<Answer>> getAnswers(@PathVariable Integer questionId) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question != null) {
            return ResponseEntity.ok(question.getAnswers());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{questionId}/answers")
    ResponseEntity<?> putAnswers(@PathVariable Integer questionId, @RequestBody Set<Answer> answers) {
        Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) return ResponseEntity.badRequest().build();
        Set<Answer> oldAnswers = answerRepository.findByQuestion(question);
        if (oldAnswers != null) {
            answerRepository.delete(oldAnswers);
        }
        //У вопроса типа TEXTAREA может быть только один ответ
        if (question.getQuestionType() == QuestionType.TEXTAREA && answers.size() > 1) {
            return ResponseEntity.badRequest().build();
        }

        for (Answer answer : answers) {
            answer.setQuestion(question);
        }
        question.setAnswers(answers);
        answerRepository.save(answers);
        questionRepository.save(question);
        if (!question.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{questionId}/answers")
    ResponseEntity<?> addAnswer(@PathVariable Integer questionId, @RequestBody Answer answer) {
        Question question = questionRepository.findByQuestionId(questionId);
        Set<Answer> answers;
        if (question == null) return ResponseEntity.badRequest().build();
        answers = question.getAnswers();
        answers.add(answer);
        answer.setQuestion(question);
        answerRepository.save(answer);
        questionRepository.save(question);
        if (!question.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/{questionId}/answers/{answerId}")
    ResponseEntity<?> putAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId, @RequestBody Answer answer) {
        Question question = questionRepository.findByQuestionId(questionId);
        Answer answerOld;
        if (question == null) return ResponseEntity.badRequest().build();
        //Получили новый правильный ответ для вопроса типа RADIO
        if (question.getQuestionType() == QuestionType.RADIO && answer.getIsRight() != null && answer.getIsRight()) {
            for (Answer elem : question.getAnswers()) {
                elem.setIsRight(false);
                answerRepository.save(elem);
            }
        }
        answerOld = answerRepository.findByAnswerId(answerId);
        if (answerOld == null) return ResponseEntity.badRequest().build();
        answerOld.update(answer);
        answerRepository.save(answerOld);
        if (!question.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @GetMapping(value = "/{questionId}/answers/{answerId}")
    ResponseEntity<?> getAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        Question question = questionRepository.findByQuestionId(questionId);
        Answer answer;
        if (question == null) return ResponseEntity.badRequest().build();
        answer = answerRepository.findByAnswerId(answerId);
        if (answer == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(answer);
    }

    @DeleteMapping(value = "/{questionId}/answers/{answerId}")
    ResponseEntity<?> deleteAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        Question question = questionRepository.findByQuestionId(questionId);
        Answer answer;
        if (question == null) return ResponseEntity.badRequest().build();
        answer = answerRepository.findByAnswerId(answerId);
        if (answer == null) return ResponseEntity.badRequest().build();

        answerRepository.delete(answer);
        if (!question.verifyAnswers()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().build();
    }
}