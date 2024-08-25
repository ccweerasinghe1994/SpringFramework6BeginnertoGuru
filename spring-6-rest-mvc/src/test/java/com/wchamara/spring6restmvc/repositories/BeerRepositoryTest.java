package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeer() {

        Beer budweiser1 = Beer.builder().
                beerName("Budweiser")
                .upc("123456789")
                .beerStyle(BeerStyle.ALE)
                .price(BigDecimal.valueOf(12.99))
                .build();
        Beer savedBudweiser = beerRepository.save(budweiser1);
        beerRepository.flush();
        assertThat(savedBudweiser).isNotNull();
        assertThat(savedBudweiser.getBeerName()).isEqualTo("Budweiser");
        assertThat(savedBudweiser.getId()).isNotNull();
    }

    @Test
    void saveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer budweiser1 = Beer.builder().
                    beerName("Budweiser123456789Budweiser123456789Budweiser123456789Budweiser123456789Budweiser123456789Budweiser123456789Budweiser123456789Budweiser123456789")
                    .upc("123456789")
                    .beerStyle(BeerStyle.ALE)
                    .price(BigDecimal.valueOf(12.99))
                    .build();
            Beer savedBudweiser = beerRepository.save(budweiser1);
            beerRepository.flush();
        });


    }
}