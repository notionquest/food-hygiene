package com.food.hygiene.controller;

import com.food.hygiene.TestAppConfig;
import com.food.hygiene.util.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RatingsController.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class RatingsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldGet500WhenLocalAuthorityIdIsNull() throws Exception {

        exception.expect(NestedServletException.class);
        exception.expectMessage(Constants.LOCAL_AUTHORITY_ID_MANDATORY);
        mockMvc.perform(post("/ratings"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldGet200WhenLocalAuthorityIdIsNotNull() throws Exception {

        MultiValueMap<String, String> inputParams = new LinkedMultiValueMap<>();
        inputParams.add("localAuthorityId", "197");
        inputParams.add("calculatorName", "Percentage");

        mockMvc.perform(post("/ratings")
                .contentType("application/x-www-form-urlencoded")
                .params(inputParams))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ratings Statistics :")))
                .andExpect(model().attribute("ratingsList", notNullValue()))
                .andExpect(model().attribute("ratingsList", hasSize(4)));
    }

    @Test
    public void shouldGet500WhenCalculatorNameIsInvalid() throws Exception {

        MultiValueMap<String, String> inputParams = new LinkedMultiValueMap<>();
        inputParams.add("localAuthorityId", "197");
        inputParams.add("calculatorName", "Percentag");

        exception.expect(NestedServletException.class);
        exception.expectMessage("No value present");
        mockMvc.perform(post("/ratings")
                .contentType("application/x-www-form-urlencoded")
                .params(inputParams))
                .andExpect(status().isInternalServerError());
    }

}