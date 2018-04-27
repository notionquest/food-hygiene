package com.food.hygiene.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Establishments implements Serializable {

    private static final long serialVersionUID = -5520416709998924100L;
    @JsonProperty("LocalAuthorityBusinessID")
    private String localAuthorityBusinessId;
    @JsonProperty("RatingKey")
    private String ratingKey;

    public String getLocalAuthorityBusinessId() {
        return localAuthorityBusinessId;
    }

    public void setLocalAuthorityBusinessId(String localAuthorityBusinessId) {
        this.localAuthorityBusinessId = localAuthorityBusinessId;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    @Override
    public String toString() {
        return "Establishments{" +
                "localAuthorityBusinessId='" + localAuthorityBusinessId + '\'' +
                ", ratingKey='" + ratingKey + '\'' +
                '}';
    }
}
