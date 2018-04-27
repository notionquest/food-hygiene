package com.food.hygiene.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommonUtilTest {

    private CommonUtil commonUtil;

    @Before
    public void setup() {
        commonUtil = new CommonUtil();
    }

    @Test
    public void shouldGetHttpHeaders() {
        HttpHeaders httpHeaders = commonUtil.getHttpHeaders();
        assertNotNull(httpHeaders);
        assertEquals(MediaType.APPLICATION_JSON, httpHeaders.getAccept().get(0));
        assertEquals(Constants.API_VERSION_VALUE, httpHeaders.get(Constants.API_VERSION_KEY).get(0));
    }

    @Test
    public void shouldGetDefaultHttpEntity() {
        HttpEntity httpEntity = commonUtil.getDefaultHttpEntity();
        assertNotNull(httpEntity);
        assertEquals(MediaType.APPLICATION_JSON, httpEntity.getHeaders().getAccept().get(0));
        assertEquals(Constants.API_VERSION_VALUE, httpEntity.getHeaders().get(Constants.API_VERSION_KEY).get(0));
    }
}