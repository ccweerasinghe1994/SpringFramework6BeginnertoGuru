package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;
    @Autowired
    private BeerRepository beerRepository;


    @Test
    void testListAllBeers() {
        List<BeerDTO> beerDTOS = beerController.listAllBeers();

        assertEquals(3, beerDTOS.size());
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = beerController.listAllBeers();
        assertThat(beerDTOS.size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void beerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testNewBeerSuccess() {

        BeerDTO newBeer1 = BeerDTO.builder().beerName("New Beer").build();
        ResponseEntity responseEntity = beerController.saveNewBeer(newBeer1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().get("Location")).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(location[location.length - 1]);

        Beer savedBeer = beerRepository.findById(savedId).get();
        assertThat(savedBeer.getBeerName()).isEqualTo("New Beer");
        assertThat(savedBeer.getId()).isEqualTo(savedId);

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateBeer() {

        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        beerDTO.setBeerName("Updated Beer");
        ResponseEntity responseEntity = beerController.updateBeer(beerDTO.getId(), beerDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beerDTO.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }
}