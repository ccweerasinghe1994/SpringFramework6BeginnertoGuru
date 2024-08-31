package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("id") UUID id) {
        log.debug("getBeerById() called in BeerController with id: {}", id);
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_PATH)
    public Page<BeerDTO> listAllBeers(
            @RequestParam(required = false) String beerName,
            @RequestParam(required = false) boolean showInventory,
            @RequestParam(required = false) BeerStyle beerStyle,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber
    ) {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers(beerName, showInventory, beerStyle, pageNumber, pageSize);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity saveNewBeer(@Validated @RequestBody BeerDTO beerDTO) {
        log.debug("saveNewBeer() called in BeerController with beer: {}", beerDTO);
        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beer/" + savedBeerDTO.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable("id") UUID id, @Validated @RequestBody BeerDTO beerDTO) {
        log.debug("updateBeer() called in BeerController with id: {} and beer: {}", id, beerDTO);
        if (beerService.updateBeer(id, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeer(@PathVariable("id") UUID id) {
        log.debug("deleteBeer() called in BeerController with id: {}", id);

        if (beerService.getBeerById(id).isEmpty()) {
            throw new NotFoundException();
        }

        beerService.deleteBeer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchBeer(@PathVariable("id") UUID id, @RequestBody BeerDTO beerDTO) {
        log.debug("patchBeer() called in BeerController with id: {} and beer: {}", id, beerDTO);

        if (beerService.getBeerById(id).isEmpty()) {
            throw new NotFoundException();
        }
        beerService.patchBeer(id, beerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
