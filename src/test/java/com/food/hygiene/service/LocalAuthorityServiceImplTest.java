package com.food.hygiene.service;

import com.food.hygiene.BaseTest;
import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.model.LocalAuthority;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocalAuthorityServiceImplTest extends BaseTest {

    private LocalAuthorityServiceImpl localAuthorityService;
    private HttpEntity httpEntity;
    private String localAuthorityUrl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CommonUtil commonUtil;

    @Before
    public void init() throws IOException {
        localAuthorityService = new LocalAuthorityServiceImpl();
        ReflectionTestUtils.setField(localAuthorityService,"apiRatingsUrl", Constants.DUMMY_URL);
        localAuthorityService.setRestTemplate(restTemplate);
        localAuthorityService.setJsonUtils(new JsonUtils());
        localAuthorityService.setCommonUtil(commonUtil);
        httpEntity = new CommonUtil().getDefaultHttpEntity();
        localAuthorityUrl = Constants.DUMMY_URL + Constants.AUTHORITIES_BASIC_URL;
    }

    @Test
    public void shouldGetLocalAuthorities() throws IOException {
        ResponseEntity<String> localAuthResponse = new ResponseEntity<>(getLocalAuthoritiesJsonFromFile(), HttpStatus.OK );
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(localAuthorityUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenReturn(localAuthResponse);
        List<LocalAuthority> localAuthorities = localAuthorityService.getLocalAuthorities();

        assertNotNull(localAuthorities);
        assertEquals(19, localAuthorities.size());
        verify(restTemplate, atLeast(1)).exchange(localAuthorityUrl,
                HttpMethod.GET,
                httpEntity, String.class);
        verify(commonUtil, atLeast(1)).getDefaultHttpEntity();
    }

    @Test
    public void shouldThrowExceptionWhenResultsAreEmpty() {

        ResponseEntity<String> localAuthResponse = new ResponseEntity<>(new String(), HttpStatus.OK );
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(localAuthorityUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenReturn(localAuthResponse);
        thrown.expect(FoodHygieneApplicationException.class);
        localAuthorityService.getLocalAuthorities();
    }

    @Test
    public void shouldThrowExceptionWhen502ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(localAuthorityUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenThrow(new RestClientException("Bad Gateway"));
        thrown.expect(RestClientException.class);
        localAuthorityService.getLocalAuthorities();
    }

    @Test
    public void shouldThrowExceptionWhen500ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(localAuthorityUrl,
                HttpMethod.GET,
                httpEntity, String.class)).thenThrow(new RestClientException("Internal server error"));
        thrown.expect(RestClientException.class);
        localAuthorityService.getLocalAuthorities();
    }

}