package com.food.hygiene.service;

import com.food.hygiene.model.LocalAuthority;
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

@Service("localAuthorityService")
public class LocalAuthorityServiceImpl implements LocalAuthorityService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${api.ratings}")
    private String apiRatingsUrl;

    @Autowired(required = true)
    private RestTemplate restTemplate;

    @Autowired(required = true)
    private JsonUtils jsonUtils;

    @Autowired(required = true)
    private CommonUtil commonUtil;

    @Override
    public List<LocalAuthority> getLocalAuthorities() {

        LOGGER.info("Get local authorities ");

        List<LocalAuthority> localAuthorities = null;

        try {

            ResponseEntity<String> response = restTemplate.exchange(apiRatingsUrl + Constants.AUTHORITIES_BASIC_URL, HttpMethod.GET, commonUtil.getDefaultHttpEntity(),String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                localAuthorities = jsonUtils.getLocalAuthorities(response.getBody());
            }

        } catch (RestClientException re) {
            LOGGER.error("Get local authorities failed ", re);
            throw re;
        }

        return localAuthorities;
    }

    public void setApiRatingsUrl(String apiRatingsUrl) {
        this.apiRatingsUrl = apiRatingsUrl;
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
