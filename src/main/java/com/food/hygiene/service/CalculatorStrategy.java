package com.food.hygiene.service;

import com.food.hygiene.model.Ratings;

import java.util.List;

public interface CalculatorStrategy {
    List<Ratings> calculate(String localAuthorityId);
    String getCalculatorName();
}
