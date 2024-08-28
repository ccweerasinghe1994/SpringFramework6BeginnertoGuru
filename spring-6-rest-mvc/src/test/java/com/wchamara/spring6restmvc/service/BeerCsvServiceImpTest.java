package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BeerCsvServiceImpTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.scv");

        List<BeerCSVRecord> recs = beerCsvService.convertCsv(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}
