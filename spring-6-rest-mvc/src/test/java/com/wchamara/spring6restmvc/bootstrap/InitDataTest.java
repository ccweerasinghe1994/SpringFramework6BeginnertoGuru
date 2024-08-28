package com.wchamara.spring6restmvc.bootstrap;

import com.wchamara.spring6restmvc.repositories.BeerRepository;
import com.wchamara.spring6restmvc.repositories.CustomerRepository;
import com.wchamara.spring6restmvc.service.BeerCsvService;
import com.wchamara.spring6restmvc.service.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class InitDataTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService csvService;

    InitData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new InitData(beerRepository, customerRepository, csvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}