package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {
        log.debug("getBeerById() called in BeerController with id: {}", id);
        return beerService.getBeerById(id);
    }

    @RequestMapping("api/v1/beer")
    public List<Beer> listAllBeers() {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers();
    }
}
