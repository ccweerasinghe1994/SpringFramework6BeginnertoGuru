package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BeerController1Test {

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBeerByIdReturnsBeer() {
        UUID beerId = UUID.randomUUID();
        Beer expectedBeer = Beer.builder()
                .id(beerId)
                .beerName("Galaxy Cat")
                .build();
        when(beerService.getBeerById(beerId)).thenReturn(expectedBeer);

        Beer actualBeer = beerController.getBeerById(beerId);

        assertEquals(expectedBeer, actualBeer);
    }

    @Test
    void getBeerByIdWithNullIdThrowsException() {
        UUID beerId = null;

        assertThrows(IllegalArgumentException.class, () -> beerController.getBeerById(beerId));
    }

    @Test
    void getBeerByIdWithNonExistentIdReturnsNull() {
        UUID beerId = UUID.randomUUID();
        when(beerService.getBeerById(beerId)).thenReturn(null);

        Beer actualBeer = beerController.getBeerById(beerId);

        assertNull(actualBeer);
    }
}