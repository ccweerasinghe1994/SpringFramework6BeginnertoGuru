package com.wchamara.spring6restmvc.bootstrap;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {


    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        Beer beer1 = Beer.builder()
                .beerName("Lion Lager")
                .beerStyle(BeerStyle.ALE)
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Carlsberg")
                .beerStyle(BeerStyle.LAGER)
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Heineken")
                .beerStyle(BeerStyle.PILSNER)
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);
    }

}
