package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeer() {

        Beer budweiser1 = Beer.builder().beerName("Budweiser").build();
        Beer savedBudweiser = beerRepository.save(budweiser1);
        beerRepository.flush();
        assertThat(savedBudweiser).isNotNull();
        assertThat(savedBudweiser.getBeerName()).isEqualTo("Budweiser");
        assertThat(savedBudweiser.getId()).isNotNull();
    }
}