package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.mapper.BeerMapper;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceImplJPA implements BeerService {

    BeerRepository beerRepository;

    BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BeerDTO> listAllBeers() {
        return List.of();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beerDTO) {

    }

    @Override
    public void deleteBeer(UUID id) {

    }

    @Override
    public void patchBeer(UUID id, BeerDTO beerDTO) {

    }
}
