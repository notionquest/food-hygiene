package com.food.hygiene.service;

import com.food.hygiene.model.Establishments;
import com.food.hygiene.model.Ratings;
import com.food.hygiene.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class RatingsPercentageCalculator extends BaseCalculator
        implements CalculatorStrategy {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private EstablishmentsService establishmentsService;
    private ScoreDescriptorService scoreDescriptorService;
    private String name = Constants.CALC_PERCENTAGE_NAME;

    @Autowired
    public RatingsPercentageCalculator(EstablishmentsService establishmentsService, ScoreDescriptorService scoreDescriptorService) {
        this.establishmentsService = establishmentsService;
        this.scoreDescriptorService = scoreDescriptorService;
    }

    /**
     * Calculate the percentage of establishments by rating key
     *
     * @param localAuthorityId
     * @return
     */

    @Override
    public List<Ratings> calculate(String localAuthorityId) {
        LOGGER.info("Calculate the percentage for local authority id :" + localAuthorityId);

        //Get establishments and group by rating key
        Map<String, Long> ratingsCount = getRatingsCount(localAuthorityId);
        Long totalEstablishmentsCount = ratingsCount.entrySet().stream().mapToLong(Map.Entry::getValue).sum();
        LOGGER.info("Total establishments count :" + totalEstablishmentsCount);

        //Format
        List<Ratings> ratingsForLocalAuthority = calculateAndFormat(ratingsCount, totalEstablishmentsCount);

        //Order ratings descending
        List<Ratings> ratingsOrdered = order(ratingsForLocalAuthority);

        LOGGER.info("Percentage calculated for local authority :" + localAuthorityId);
        return ratingsOrdered;
    }

    @Override
    public String getCalculatorName() {
        return name;
    }


    /**
     * Get the score descriptor, match the rating key and calculate the
     * percentage
     *
     * @param ratingsCount
     * @param totalEstablishmentsCount
     * @return
     */
    private List<Ratings> calculateAndFormat(Map<String, Long> ratingsCount,
                                             Long totalEstablishmentsCount) {

        LOGGER.info("Format ratings ...");
        List<Ratings> ratingsForLocalAuthority = new ArrayList<>();

        Map<String, String> scoreDescriptor = scoreDescriptorService.getScoreDecriptor();

        //Matching the rating key with rating name (e.g. 'fhrs_5_en-gb' with 5)
        if (scoreDescriptor != null && !scoreDescriptor.isEmpty()) {
            for (Map.Entry<String, Long> rating : ratingsCount.entrySet()) {
                if (scoreDescriptor.containsKey(rating.getKey())) {
                    Ratings ratings = new Ratings();
                    String ratingKey = scoreDescriptor.get(rating.getKey());
                    if (ratingKey.length() > 1) {
                        //Use the rating text as is (e.g. Exempt)
                        ratings.setRating(ratingKey);
                    } else {
                        //Append the STAR string for numeric ratings (e.g. 1, 2 etc.)
                        ratings.setRating(ratingKey.concat(Constants.STAR));
                    }
                    //Calculate the percentage
                    double percentageDouble = (rating.getValue().doubleValue() / totalEstablishmentsCount.doubleValue()) * 100;
                    BigDecimal percentage = new BigDecimal(percentageDouble);
                    //Format to 2 decimal places
                    ratings.setValue(percentage.setScale(2, RoundingMode.HALF_UP).doubleValue());
                    ratingsForLocalAuthority.add(ratings);
                }
            }
        }

        return ratingsForLocalAuthority;
    }

    /**
     * Get the establishments and group by rating key
     *
     * @param localAuthorityId
     * @return
     */

    private Map<String, Long> getRatingsCount(String localAuthorityId) {
        LOGGER.info("Get ratings count ...");
        int pageNumber = 1;
        List<Establishments> establishments = new ArrayList<>();
        Map<String, Long> consolidatedRatingsMap = new HashMap<>();
        BinaryOperator<Long> adder = (n1, n2) -> n1 + n2;
        do {
            establishments.clear();
            establishments = establishmentsService.getEstablishments(localAuthorityId, pageNumber);
            if (establishments != null && !establishments.isEmpty()) {
                //Group the establishments by ratings key and count
                Map<String, Long> establishmentsGroupByRatingsKey = establishments.stream().collect(
                        groupingBy(Establishments::getRatingKey,
                                counting()));
                //Merging the count with previous iteration values
                Map<String, Long> tempMap = Stream.of(consolidatedRatingsMap,
                        establishmentsGroupByRatingsKey).flatMap(m -> m.entrySet().stream())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, adder));
                //Copying the values from temporary map to consolidated map
                consolidatedRatingsMap.clear();
                consolidatedRatingsMap.putAll(tempMap);
                tempMap.clear();
                pageNumber++;
            }
        } while (establishments != null && establishments.size() > 0);
        LOGGER.info("Get ratings count end ... ");
        return consolidatedRatingsMap;
    }
}
