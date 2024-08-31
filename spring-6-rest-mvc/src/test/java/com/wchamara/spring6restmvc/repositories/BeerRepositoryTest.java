package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.bootstrap.InitData;
import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({InitData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void getBeerByBeerName() {
        List<Beer> allByBeerNameIsLikeIgnoreCase = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");

        assertThat(allByBeerNameIsLikeIgnoreCase.size()).isEqualTo(336);
    }


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