package com.food.hygiene.model;

import java.io.Serializable;

public class Ratings implements Serializable {
    private static final long serialVersionUID = -2605315199703250482L;

    private String rating;
    private Double value;

    public Ratings() {
    }

    public Ratings(String rating, Double value) {
        this.rating = rating;
        this.value = value; 
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "rating='" + rating + '\'' +
                ", value=" + value +
                '}';
    }
}
