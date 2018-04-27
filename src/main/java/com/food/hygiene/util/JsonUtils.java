package com.food.hygiene.util;

import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.model.Establishments;
import com.food.hygiene.model.LocalAuthority;
import com.food.hygiene.model.ScoreDescriptor;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public List<LocalAuthority> getLocalAuthorities(String localAuthoritiesJson) {

        List<LocalAuthority> localAuthorities = null;
        try {
            TypeRef<List<LocalAuthority>> typeRef = new TypeRef<List<LocalAuthority>>() {
            };
            localAuthorities = JsonPath.using(JACKSON_CONFIGURATION)
                    .parse(localAuthoritiesJson)
                    .read("$.authorities", typeRef);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unable to parse the local authorities json", e);
            throw new FoodHygieneApplicationException(e);
        }

        return localAuthorities;
    }

    public Map<String, String> getRatings(String ratingsJson) {

        Map<String, String> ratingsMap = null;
        List<ScoreDescriptor> ratings = null;
        try {
            TypeRef<List<ScoreDescriptor>> typeRef = new TypeRef<List<ScoreDescriptor>>() {
            };
            ratings = JsonPath.using(JACKSON_CONFIGURATION)
                    .parse(ratingsJson)
                    .read("$.ratings", typeRef);

            ratingsMap = ratings.stream().collect(Collectors.toMap(ScoreDescriptor::getRatingKey, ScoreDescriptor::getRatingName));

        } catch (IllegalArgumentException e) {
            LOGGER.error("Unable to parse the ratings json", e);
            throw new FoodHygieneApplicationException(e);
        }

        return ratingsMap;
    }

    public List<Establishments> getEstablishments(String establishmentsJson) {

        List<Establishments> establishments = null;
        try {
            TypeRef<List<Establishments>> typeRef = new TypeRef<List<Establishments>>() {
            };
            establishments = JsonPath.using(JACKSON_CONFIGURATION)
                    .parse(establishmentsJson)
                    .read("$.establishments", typeRef);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unable to parse the establishments json", e);
            throw new FoodHygieneApplicationException(e);
        }

        return establishments;
    }


    public static final Configuration JACKSON_CONFIGURATION = Configuration
            .builder()
            .mappingProvider(new JacksonMappingProvider())
            .jsonProvider(new JacksonJsonProvider())
            .build();
}
