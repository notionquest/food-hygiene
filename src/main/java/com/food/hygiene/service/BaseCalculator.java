package com.food.hygiene.service;

import com.food.hygiene.model.Ratings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseCalculator {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Order the ratings by ratings key by descending
     *
     * @param ratingsForLocalAuthority
     * @return
     */
    protected List<Ratings> order(List<Ratings> ratingsForLocalAuthority) {
        LOGGER.info("Order ratings by rating key descending ...");
        List<Ratings> ratingsOrdered = null;
        if (ratingsForLocalAuthority != null && !ratingsForLocalAuthority.isEmpty()) {
            ratingsOrdered = ratingsForLocalAuthority.stream().sorted(Comparator.comparing(Ratings::getRating).reversed()).collect(Collectors.toList());
        }
        return ratingsOrdered;
    }
}
