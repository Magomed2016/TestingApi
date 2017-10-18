package ru.pflb.testingapp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by insider on 12.09.2017.
 */
public enum QuestionType {
    @JsonProperty("0")
    RADIO,
    @JsonProperty("1")
    CHECKBOX,
    @JsonProperty("2")
    TEXTAREA
}
