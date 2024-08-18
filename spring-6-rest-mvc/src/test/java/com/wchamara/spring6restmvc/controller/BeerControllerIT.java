package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}