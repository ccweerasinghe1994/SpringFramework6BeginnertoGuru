package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);

    List<Beer> listAllBeers();
}
