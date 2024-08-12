package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping("{id}")
    public Beer getBeerById(@PathVariable("id") UUID id) {
        log.debug("getBeerById() called in BeerController with id: {}", id);
        return beerService.getBeerById(id);
    }

    @GetMapping
    public List<Beer> listAllBeers() {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers();
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody Beer beer) {
        log.debug("saveNewBeer() called in BeerController with beer: {}", beer);
        Beer savedBeer = beerService.saveNewBeer(beer);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
