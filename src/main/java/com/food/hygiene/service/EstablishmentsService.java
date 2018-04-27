package com.food.hygiene.service;

import com.food.hygiene.model.Establishments;

import java.util.List;

public interface EstablishmentsService {

    List<Establishments> getEstablishments(String localAuthorityId, Integer pageNumber);
}
