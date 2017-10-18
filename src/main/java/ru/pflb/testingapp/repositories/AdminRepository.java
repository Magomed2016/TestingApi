package ru.pflb.testingapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pflb.testingapp.entities.Admin;
import ru.pflb.testingapp.entities.Answer;
import ru.pflb.testingapp.entities.Question;

import java.util.Collection;
import java.util.Set;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminId(Integer adminId);
    Admin findByAdminLogin(String adminLogin);

    //Set<Answer> findByQuestion(Question question);
}


