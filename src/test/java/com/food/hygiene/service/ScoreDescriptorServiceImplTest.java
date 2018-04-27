package com.food.hygiene.service;

import com.food.hygiene.BaseTest;
import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.util.CommonUtil;
import com.food.hygiene.util.Constants;
import com.food.hygiene.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class ScoreDescriptorServiceImplTest extends BaseTest {

    private ScoreDescriptorServiceImpl scoreDescriptorService;
    private HttpEntity httpEntity;
    private String scoreDescriptorUrl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CommonUtil commonUtil;

    @Before
    public void init() {
        scoreDescriptorService = new ScoreDescriptorServiceImpl();
        ReflectionTestUtils.setField(scoreDescriptorService,"apiRatingsUrl", Constants.DUMMY_URL);
        scoreDescriptorService.setRestTemplate(restTemplate);
        scoreDescriptorService.setJsonUtils(new JsonUtils());
        scoreDescriptorService.setCommonUtil(commonUtil);
        httpEntity = new CommonUtil().getDefaultHttpEntity();
        scoreDescriptorUrl = Constants.DUMMY_URL + Constants.RATINGS_URL;
    }

    @Test
    public void shouldGetScoreDescriptors() throws IOException {
        ResponseEntity<String> scoreDescriptorResponse = new ResponseEntity<>(getScoreDescriptorsJsonFromFile(), HttpStatus.OK );
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(scoreDescriptorUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenReturn(scoreDescriptorResponse);
        Map<String,String> scoreDecriptor = scoreDescriptorService.getScoreDecriptor();

        assertNotNull(scoreDecriptor);
        assertEquals(11, scoreDecriptor.size());

        verify(commonUtil, atLeast(1)).getDefaultHttpEntity();
        verify(restTemplate, atLeast(1)).exchange(scoreDescriptorUrl,
                HttpMethod.GET,
                httpEntity, String.class);
    }

    @Test
    public void shouldThrowExceptionWhenResultsAreEmpty() {

        ResponseEntity<String> scoreDescriptorResponse = new ResponseEntity<>(new String(), HttpStatus.OK );
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(scoreDescriptorUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenReturn(scoreDescriptorResponse);
        thrown.expect(FoodHygieneApplicationException.class);
        scoreDescriptorService.getScoreDecriptor();
    }

    @Test
    public void shouldThrowExceptionWhen502ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(scoreDescriptorUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenThrow(new RestClientException("Bad Gateway"));
        thrown.expect(RestClientException.class);
        scoreDescriptorService.getScoreDecriptor();
    }

    @Test
    public void shouldThrowExceptionWhen500ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(scoreDescriptorUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenThrow(new RestClientException("Internal server error"));
        thrown.expect(RestClientException.class);
        scoreDescriptorService.getScoreDecriptor();
    }


}