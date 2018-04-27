package com.food.hygiene.controller;

import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.model.Authority;
import com.food.hygiene.model.Ratings;
import com.food.hygiene.service.CalculatorStrategy;
import com.food.hygiene.service.RatingsPercentageCalculator;
import com.food.hygiene.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RatingsController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private List<CalculatorStrategy> calculatorStrategyList;

    @Autowired
    public RatingsController(RatingsPercentageCalculator ratingsPercentageCalculator) {
        this.calculatorStrategyList = new ArrayList<>();
        calculatorStrategyList.add(ratingsPercentageCalculator);
    }

    @PostMapping("/ratings")
    public ModelAndView getRatings(@ModelAttribute Authority authority) {
        LOGGER.info("Get ratings :" + authority.getLocalAuthorityId());
        ModelAndView mv = new ModelAndView("ratings");
        if (authority.getLocalAuthorityId() != null
                && authority.getCalculatorName() != null) {
            CalculatorStrategy calculatorStrategy = calculatorStrategyList.stream().filter(e -> e.getCalculatorName().equalsIgnoreCase(authority.getCalculatorName())).findFirst().get();
            mv.addObject("ratingsList",
                    calculatorStrategy.calculate(authority.getLocalAuthorityId()));
        } else {
            throw new FoodHygieneApplicationException(Constants.LOCAL_AUTHORITY_ID_MANDATORY);
        }

        LOGGER.info("Get ratings end ...");
        return mv;
    }

    @Deprecated
    protected List<Ratings> getRatings() {
        List<Ratings> ratings = new ArrayList<>();
        ratings.add(new Ratings("5-star", 50.0));
        ratings.add(new Ratings("4-star", 0.0));
        ratings.add(new Ratings("3-star", 0.0));
        ratings.add(new Ratings("2-star", 0.0));
        ratings.add(new Ratings("1-star", 25.0));
        ratings.add(new Ratings("Exempt", 25.0));
        return ratings;
    }

}
