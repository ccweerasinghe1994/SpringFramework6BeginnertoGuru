package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerTest {
    @Autowired
    private BeerController beerController;

    @Test
    void getBeerByIdReturnsBeer() {
        UUID beerId = UUID.randomUUID();
        Beer expectedBeer = Beer.builder()
                .id(beerId)
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(BigDecimal.valueOf(12.95))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer actualBeer = beerController.getBeerById(beerId);

        assertEquals(expectedBeer.getBeerName(), actualBeer.getBeerName());
        assertEquals(expectedBeer.getBeerStyle(), actualBeer.getBeerStyle());
        assertEquals(expectedBeer.getUpc(), actualBeer.getUpc());
        assertEquals(expectedBeer.getPrice(), actualBeer.getPrice());
        assertEquals(expectedBeer.getQuantityOnHand(), actualBeer.getQuantityOnHand());
    }

    @Test
    void getBeerByIdWithNullIdThrowsException() {
        UUID beerId = null;

        assertThrows(IllegalArgumentException.class, () -> beerController.getBeerById(beerId));
    }

    @Test
    void getBeerByIdWithNonExistentIdReturnsNull() {
        UUID beerId = UUID.randomUUID();


        Beer actualBeer = beerController.getBeerById(beerId);

        assertNull(actualBeer);
    }
}