package ru.pflb.testingapp.controllers;



import jdk.nashorn.internal.runtime.options.LoggingOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pflb.testingapp.entities.Admin;
import ru.pflb.testingapp.entities.TestPerson;
import ru.pflb.testingapp.repositories.AdminRepository;
import ru.pflb.testingapp.repositories.TestPersonRepository;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by insider on 13.09.2017.
 */
@RestController
@RequestMapping("/api/user")
public class TestPersonController {
    private final TestPersonRepository testPersonRepository;


    @Autowired
    public TestPersonController(TestPersonRepository testPersonRepository) {
        this.testPersonRepository = testPersonRepository;
    }



    @PostMapping
    @ResponseBody
    ResponseEntity<Integer> addTestPerson(@RequestBody TestPerson testPerson){
        testPersonRepository.save(testPerson);
        return ResponseEntity.ok(testPerson.getTestPersonId());
    }


    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<TestPerson>> show(){
        List<TestPerson> list = testPersonRepository.findAll();

        return ResponseEntity.ok(list);
    }


    @GetMapping(value = "/{userId}")
    @ResponseBody
    ResponseEntity<TestPerson> getStatistic(@PathVariable Integer userId){
        TestPerson testPerson= testPersonRepository.findBytestPersonId(userId);
        return ResponseEntity.ok(testPerson);
    }




}
