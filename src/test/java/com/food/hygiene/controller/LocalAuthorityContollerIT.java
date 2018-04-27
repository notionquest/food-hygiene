package com.food.hygiene.controller;

import com.food.hygiene.TestAppConfig;
import com.food.hygiene.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LocalAuthorityContoller.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class LocalAuthorityContollerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetLocalAuthorities() throws Exception {

        mockMvc.perform(get(Constants.LOCAL_AUTHORITY_URL))
                .andExpect(content().string(containsString("Local Authorities :")))
                .andExpect(model().attribute("authorities", notNullValue()))
                .andExpect(model().attribute("authorities", hasSize(392)));
    }

}