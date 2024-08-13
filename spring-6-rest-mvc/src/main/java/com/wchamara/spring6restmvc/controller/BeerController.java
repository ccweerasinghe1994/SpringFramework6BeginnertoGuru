package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("id") UUID id) {
        log.debug("getBeerById() called in BeerController with id: {}", id);
        return beerService.getBeerById(id);
    }

    @GetMapping(BEER_PATH)
    public List<Beer> listAllBeers() {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers();
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity saveNewBeer(@RequestBody Beer beer) {
        log.debug("saveNewBeer() called in BeerController with beer: {}", beer);
        Beer savedBeer = beerService.saveNewBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        log.debug("updateBeer() called in BeerController with id: {} and beer: {}", id, beer);
        beerService.updateBeer(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeer(@PathVariable("id") UUID id) {
        log.debug("deleteBeer() called in BeerController with id: {}", id);
        beerService.deleteBeer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchBeer(@PathVariable("id") UUID id, @RequestBody Beer beer) {
        log.debug("patchBeer() called in BeerController with id: {} and beer: {}", id, beer);
        beerService.patchBeer(id, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
