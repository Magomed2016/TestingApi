package ru.pflb.testingapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pflb.testingapp.entities.*;
import ru.pflb.testingapp.entity_wrappers.QuestionFull;
import ru.pflb.testingapp.entity_wrappers.Resolve.ResolveCheckBox;
import ru.pflb.testingapp.entity_wrappers.Resolve.ResolveRadio;
import ru.pflb.testingapp.entity_wrappers.Resolve.ResolveTextArea;
import ru.pflb.testingapp.entity_wrappers.Resolves;
import ru.pflb.testingapp.repositories.AnswerRepository;
import ru.pflb.testingapp.repositories.QuestionRepository;
import ru.pflb.testingapp.repositories.StatisticRepository;
import ru.pflb.testingapp.repositories.TopicRepository;

import java.io.IOException;
import java.util.*;

import static java.lang.Character.isDigit;

/**
 * Created by insider on 16.09.2017.
 */
@RestController
@RequestMapping(value = "api/testing")
public class TestingController {
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;
    private final StatisticRepository statisticRepository;

    @Autowired
    public TestingController(QuestionRepository questionRepository, TopicRepository topicRepository, AnswerRepository answerRepository, StatisticRepository statisticRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.statisticRepository = statisticRepository;
    }

    @GetMapping(value = "/questions/{questionId}")
    ResponseEntity<QuestionFull> getQuestion(@PathVariable Integer questionId){
        Question question = questionRepository.findByQuestionId(questionId);
        if (question == null) return ResponseEntity.notFound().build();
        QuestionFull questionFull = new QuestionFull(question);
        return ResponseEntity.ok(questionFull);
    }

    @GetMapping(value = "/topics/{topicId}/questions")
    ResponseEntity<List<Integer>> getQuestionList(@PathVariable Integer topicId){
        Set<Question> questionSet = topicRepository.findByTopicId(topicId).getQuestions();
        List<Integer> questionIdList = new ArrayList<>();
        for (Question question: questionSet){
            questionIdList.add(question.getQuestionId());
        }
        return ResponseEntity.ok(questionIdList);
    }


    /////////////////////////
    //ПРОВЕРКА ЗДЕСЬ////////
    ////////////////////////

    @PostMapping(value = "/end")
    @ResponseBody
    public void chekingResolves(@RequestBody Resolves resolves) throws IOException {

       // System.out.println(resolves.getResolves());
        Topic topic = topicRepository.findByTopicId(resolves.getTopicId());
        int size = topic.getQuestions().size();
        System.out.println("===============================================");
        System.out.println("Количество вопросов в топике: "+ size );
        System.out.println("===============================================");

        String str = resolves.getResolves().substring(1,(resolves.getResolves().length()-1));
        String newStr[]=str.split(";");
        ObjectMapper objectMapper = new ObjectMapper();
        int countRightAnswers = 0;

        for (String s : newStr) {
            System.out.println("начал "+s);

            char[] arr = s.toCharArray();
            for(int i=0; i<arr.length;i++){
                if(isDigit(arr[i])){
                    int questionId = Integer.parseInt(String.valueOf(arr[i]));
                    Question question = questionRepository.findByQuestionId(questionId);
                    if(question.getQuestionType().equals(QuestionType.RADIO)){
                        ResolveRadio resolveRadio = objectMapper.readValue(s,ResolveRadio.class);
                        Answer answer = answerRepository.findByAnswerId(resolveRadio.getAnswerId());
                        //System.out.println(answer.getIsRight());
                        //System.out.println(resolveRadio.getQuestionId());
                        if(answer.getIsRight()){
                            countRightAnswers++;
                           // System.out.println("true");
                        }
                    }
                    else if(question.getQuestionType().equals(QuestionType.CHECKBOX)){
                      /*  ResolveCheckBox resolveCheckBox = objectMapper.readValue(s,ResolveCheckBox.class);
                        HashMap<String, Integer> hm = resolveCheckBox.getMapAnswersId();


                        Set<Map.Entry<String, Integer>> set = hm.entrySet();
                        for (Map.Entry<String, Integer> o : set) {
                            System.out.println(o.getValue());
                        }*/
                        String ns = s.substring(0,s.length()-1);
                      String news[] = ns.split(",");
                        boolean isTrue = true;
                        for (int j = 1; j < news.length; j++) {

                            String string = news[0]+","+news[j]+"}";
                           // System.out.println(string);
                            ResolveRadio resolveRadio = objectMapper.readValue(string,ResolveRadio.class);
                            Answer answer = answerRepository.findByAnswerId(resolveRadio.getAnswerId());
                            //System.out.println(answer.getIsRight());
                            //System.out.println(resolveRadio.getQuestionId());

                            if(!answer.getIsRight()){
                                isTrue = false;
                                break;
                                // System.out.println("true");

                            }
                        }

                        if (isTrue==true){
                            countRightAnswers++;
                        }



                        /*Answer answer = answerRepository.findByAnswerId(resolveCheckBox.getAnswerId());
                        if(answer.getIsRight()){
                            countRightAnswers++;
                          //  System.out.println("true");
                        }*/

                        //System.out.println(resolveCheckBox.getQuestionId());
                    }
                    else {
                        ResolveTextArea resolveTextArea = objectMapper.readValue(s,ResolveTextArea.class);
                        Answer answer = answerRepository.findByAnswerId(resolveTextArea.getAnswerId());
                        if(answer.getText().equals(resolveTextArea.getAnswer())){
                            countRightAnswers++;
                           // System.out.println("true");
                        }
                       // System.out.println(answer.getAnswerId());

                        // System.out.println(resolveTextArea.getQuestionId());
                    }
                    break;
                }

            }
        }

        int statistic = (int)(((double)countRightAnswers/(double)size)*100);
        System.out.println("================================================");

        System.out.println("Количество правильных ответов: "+ countRightAnswers);
        System.out.println("Таким образом статистика такова: "+ statistic+"% правильных ответов");
        System.out.println("================================================");

        Statistic newStatistic = new Statistic(null, resolves.getTopicId(),resolves.getPersonId(),statistic+"%");
        statisticRepository.save(newStatistic);



        //System.out.println(newStr[0]);



    }


    public void sendMessage(){


    }


    @GetMapping(value = "/getStatistic/")
    @ResponseBody
    ResponseEntity<List<Statistic>> getStatistics(){
        List<Statistic> list = statisticRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/getStatistic/{statisticId}")
    @ResponseBody
    ResponseEntity<Statistic> getStatistic(@PathVariable Integer statisticId){
        Statistic statistic = statisticRepository.findByStatisticId(statisticId);
        return ResponseEntity.ok(statistic);
    }
}
