package com.food.hygiene.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDescriptor implements Serializable {
    @JsonProperty("ratingKey")
    private String ratingKey;
    @JsonProperty("ratingName")
    private String ratingName;

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getRatingName() {
        return ratingName;
    }

    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }

    @Override
    public String toString() {
        return "ScoreDescriptor{" +
                "ratingKey='" + ratingKey + '\'' +
                ", ratingName='" + ratingName + '\'' +
                '}';
    }
}
