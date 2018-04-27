package com.food.hygiene.service;

import com.food.hygiene.model.Establishments;
import com.food.hygiene.util.CommonUtil;
import com.food.hygiene.util.Constants;
import com.food.hygiene.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("establishmentsService")
public class EstablishmentsServiceImpl implements EstablishmentsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${api.ratings}")
    private String apiRatingsUrl;

    @Value("${establishments.page.size}")
    private Integer pageSize;

    @Autowired(required = true)
    private RestTemplate restTemplate;

    @Autowired(required = true)
    private JsonUtils jsonUtils;

    @Autowired(required = true)
    private CommonUtil commonUtil;

    @Override
    public List<Establishments> getEstablishments(String localAuthorityId, Integer pageNumber) {


        LOGGER.info("Get establishments for local authority id :" + localAuthorityId);
        LOGGER.info("Page Number :" + pageNumber);
        LOGGER.info("Page size :" + pageSize);

        List<Establishments> establishments = null;

        try {

            ResponseEntity<String> response = restTemplate.exchange(apiRatingsUrl + Constants.ESTABLISHMENTS_URL, HttpMethod.GET,
                    commonUtil.getDefaultHttpEntity(), String.class,
                    localAuthorityId,
                    pageNumber,
                    pageSize);

            if (response.getStatusCode() == HttpStatus.OK) {
                establishments = jsonUtils.getEstablishments(response.getBody());
            }

        } catch (RestClientException re) {
            LOGGER.error("Get establishments failed ", re);
            throw re;
        }

        return establishments;

    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setJsonUtils(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    public void setCommonUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }
}
