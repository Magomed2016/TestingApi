package ru.pflb.testingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "TestPerson")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

public class TestPerson  implements java.io.Serializable{


    @Id
    @GeneratedValue
    @Column(name = "testPersonId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer testPersonId;



    @Column(name = "testPersonName", unique = false, nullable = false)
    @NonNull
    private String testPersonName;

    @Column(name = "testPersonSurname", unique = false, nullable = false)
    @NonNull
    private String  testPersonSurname;



    @Column(name = "testPersonMail", unique = false, nullable = false)
    @NonNull
    private String  testPersonMail;




}
