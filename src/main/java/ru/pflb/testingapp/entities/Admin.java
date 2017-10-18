package ru.pflb.testingapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "Admin")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

public class Admin implements java.io.Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "adminId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer adminId;


    @Column(name = "adminLogin", unique = false, nullable = false)
    @NonNull
    private String adminLogin;

    @Column(name = "adminPassword", unique = false, nullable = false)
    @NonNull
    private String  adminPassword;
}
