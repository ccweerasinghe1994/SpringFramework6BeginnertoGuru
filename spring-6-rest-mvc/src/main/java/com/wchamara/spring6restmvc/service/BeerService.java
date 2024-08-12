package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);

    List<Beer> listAllBeers();

    Beer saveNewBeer(Beer beer);

    void updateBeer(UUID id, Beer beer);

    void deleteBeer(UUID id);

    void patchBeer(UUID id, Beer beer);
}
