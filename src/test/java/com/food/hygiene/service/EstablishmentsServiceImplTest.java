package com.food.hygiene.service;

import com.food.hygiene.BaseTest;
import com.food.hygiene.exception.FoodHygieneApplicationException;
import com.food.hygiene.model.Establishments;
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
public class EstablishmentsServiceImplTest extends BaseTest {

    private EstablishmentsServiceImpl establishmentsService;
    private HttpEntity httpEntity;
    private String establishmentsUrl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CommonUtil commonUtil;

    private String localAuthorityId = "197";
    private Integer pageNumber = 1;
    private Integer pageSize = 10;

    @Before
    public void init() {
        establishmentsService = new EstablishmentsServiceImpl();

        ReflectionTestUtils.setField(establishmentsService, "apiRatingsUrl", Constants.DUMMY_URL);
        ReflectionTestUtils.setField(establishmentsService, "pageSize", pageSize);
        establishmentsService.setRestTemplate(restTemplate);
        establishmentsService.setJsonUtils(new JsonUtils());
        establishmentsService.setCommonUtil(commonUtil);
        httpEntity = new CommonUtil().getDefaultHttpEntity();
        establishmentsUrl = Constants.DUMMY_URL + Constants.ESTABLISHMENTS_URL;
    }

    @Test
    public void shouldGetEstablishments() throws IOException {
        ResponseEntity<String> establishmentsResponse = new ResponseEntity<>(getEstablishmentsJsonFromFile(), HttpStatus.OK);
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(establishmentsUrl,
                HttpMethod.GET,
                httpEntity, String.class,
                localAuthorityId,
                pageNumber,
                pageSize
        )).thenReturn(establishmentsResponse);
        List<Establishments> establishments = establishmentsService.getEstablishments(localAuthorityId, pageNumber);

        assertNotNull(establishments);
        assertEquals(10, establishments.size());
        verify(restTemplate, atLeast(1)).exchange(
                establishmentsUrl,
                HttpMethod.GET,
                httpEntity, String.class,
                localAuthorityId,
                pageNumber,
                pageSize);
        verify(commonUtil, atLeast(1)).getDefaultHttpEntity();
    }

    @Test
    public void shouldThrowExceptionWhenResultsAreEmpty() {

        ResponseEntity<String> establishmentsResponse = new ResponseEntity<>(new String(), HttpStatus.OK);
        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(establishmentsUrl,
                HttpMethod.GET,
                httpEntity, String.class,
                localAuthorityId,
                pageNumber,
                pageSize
        )).thenReturn(establishmentsResponse);
        thrown.expect(FoodHygieneApplicationException.class);
        establishmentsService.getEstablishments(localAuthorityId, pageNumber);
    }

    @Test
    public void shouldThrowExceptionWhen502ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(establishmentsUrl,
                HttpMethod.GET,
                httpEntity, String.class,
                localAuthorityId,
                pageNumber,
                pageSize)).thenThrow(new RestClientException("Bad Gateway"));
        thrown.expect(RestClientException.class);
        establishmentsService.getEstablishments(localAuthorityId, pageNumber);
    }

    @Test
    public void shouldThrowExceptionWhen500ReturnedForRemoteService() {

        when(commonUtil.getDefaultHttpEntity()).thenReturn(httpEntity);
        when(restTemplate.exchange(establishmentsUrl,
                HttpMethod.GET,
                httpEntity, String.class,
                localAuthorityId,
                pageNumber,
                pageSize)).thenThrow(new RestClientException("Internal server error"));
        thrown.expect(RestClientException.class);
        establishmentsService.getEstablishments(localAuthorityId, pageNumber);
    }


}