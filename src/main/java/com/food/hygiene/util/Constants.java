package com.food.hygiene.util;

public interface Constants {
    String AUTHORITIES_BASIC_URL = "/Authorities/basic";
    String RATINGS_URL = "/Ratings";
    String ESTABLISHMENTS_URL = "/Establishments?localAuthorityId={localAuthorityId}&pageNumber={pageNo}&pageSize={pageSize}";
    String API_VERSION_KEY = "x-api-version";
    String API_VERSION_VALUE = "2";
    String DUMMY_URL = "http://dummy/url";
    String STAR = "-STAR";
    String LOCAL_AUTHORITY_ID_MANDATORY = "Local authority id is mandatory";
    String LOCAL_AUTHORITY_URL = "/local/authority";
    String CALC_PERCENTAGE_NAME = "Percentage";
}
