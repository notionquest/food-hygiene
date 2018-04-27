package com.food.hygiene.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalAuthority implements Serializable {

    private static final long serialVersionUID = -7509835183150057742L;

    @JsonProperty("LocalAuthorityId")
    private Integer localAuthorityId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("EstablishmentCount")
    private Long establishmentCount;

    public LocalAuthority() {
    }

    public LocalAuthority(Integer localAuthorityId, String name, Long establishmentCount) {
        this.localAuthorityId = localAuthorityId;
        this.name = name;
        this.establishmentCount = establishmentCount;
    }

    public Integer getLocalAuthorityId() {
        return localAuthorityId;
    }

    public void setLocalAuthorityId(Integer localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEstablishmentCount() {
        return establishmentCount;
    }

    public void setEstablishmentCount(Long establishmentCount) {
        this.establishmentCount = establishmentCount;
    }

    @Override
    public String toString() {
        return "LocalAuthorities{" +
                "localAuthorityId=" + localAuthorityId +
                ", name='" + name + '\'' +
                ", establishmentCount=" + establishmentCount +
                '}';
    }
}

