package com.food.hygiene.util;

import com.food.hygiene.BaseTest;
import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.model.Establishments;
import com.food.hygiene.model.LocalAuthority;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JsonUtilsTest extends BaseTest {

    private JsonUtils jsonUtils;

    @Before
    public void setUp() {
        jsonUtils = new JsonUtils();
    }

    @Test
    public void shouldGetLocalAuthorities() throws IOException, FoodHygieneApplicationException {
        List<LocalAuthority> localAuthorities = jsonUtils.getLocalAuthorities(getLocalAuthoritiesJsonFromFile());
        assertNotNull(localAuthorities);
        assertEquals(19, localAuthorities.size());
    }

    @Test
    public void shouldLocalAuthoritiesThrowExceptionWhenJsonIsNull() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("json string can not be null or empty");
        jsonUtils.getLocalAuthorities(null);
    }

    @Test
    public void shouldLocalAuthoritiesThrowExceptionWhenJsonIsEmpty() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("");
        jsonUtils.getLocalAuthorities("");
    }

    @Test
    public void shouldGetEstablishments() throws IOException, FoodHygieneApplicationException {
        List<Establishments> establishments = jsonUtils.getEstablishments(getEstablishmentsJsonFromFile());
        assertNotNull(establishments);
        assertEquals(10, establishments.size());
    }

    @Test
    public void shouldEstablishmentsThrowExceptionWhenJsonIsNull() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("json string can not be null or empty");
        jsonUtils.getEstablishments(null);
    }

    @Test
    public void shouldEstablishmentsThrowExceptionWhenJsonIsEmpty() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("");
        jsonUtils.getEstablishments("");
    }

    @Test
    public void shouldGetRatings() throws IOException, FoodHygieneApplicationException {
        Map<String, String> ratings = jsonUtils.getRatings(getScoreDescriptorsJsonFromFile());
        assertNotNull(ratings);
        assertEquals(11, ratings.size());
    }

    @Test
    public void shouldRatingsThrowExceptionWhenJsonIsNull() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("json string can not be null or empty");
        jsonUtils.getRatings(null);
    }

    @Test
    public void shouldRatingsThrowExceptionWhenJsonIsEmpty() {
        thrown.expect(FoodHygieneApplicationException.class);
        thrown.expectMessage("");
        jsonUtils.getRatings("");
    }


}