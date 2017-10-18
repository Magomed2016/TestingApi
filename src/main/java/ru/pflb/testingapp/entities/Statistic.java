package ru.pflb.testingapp.entities;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "Statistic")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

public class Statistic implements java.io.Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "statisticId", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer statisticId;



    @Column(name = "topicId", unique = false, nullable = false)
    @NonNull
    private Integer topicId;


    @Column(name = "testPersonId", unique = false, nullable = false)
    @NonNull
    private Integer testPersonId;


    @Column(name = "statisticPerson", unique = false, nullable = false)
    @NonNull
    private String statistic;

    public Statistic(Integer statisticId, Integer topicId, Integer testPersonId, String statistic) {
        this.statisticId = statisticId;
        this.topicId = topicId;
        this.testPersonId = testPersonId;
        this.statistic = statistic;
    }
}
