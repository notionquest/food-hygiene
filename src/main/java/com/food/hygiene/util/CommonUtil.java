package com.food.hygiene.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Component
public class CommonUtil {

    public HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set(Constants.API_VERSION_KEY, Constants.API_VERSION_VALUE);

        return httpHeaders;
    }

    public HttpEntity getDefaultHttpEntity() {
        HttpEntity httpEntity = new HttpEntity(getHttpHeaders());
        return httpEntity;
    }
}
