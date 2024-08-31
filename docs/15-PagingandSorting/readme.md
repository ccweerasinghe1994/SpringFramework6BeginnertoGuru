#

## 001 Introduction
![alt text](image.png)

## 002 What is Paging and Sorting
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)

## 003 Paging and Sorting with Spring

![alt text](image-5.png)
![alt text](image-6.png)
![alt text](image-7.png)
![alt text](image-8.png)
## 004 Add Paging Parameters
```java
    @GetMapping(BEER_PATH)
    public List<BeerDTO> listAllBeers(
            @RequestParam(required = false) String beerName,
            @RequestParam(required = false) boolean showInventory,
            @RequestParam(required = false) BeerStyle beerStyle,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber
    ) {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers(beerName, showInventory, beerStyle, pageSize, pageNumber);
    }
```


```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listAllBeers(String beerName, Boolean showInventory, BeerStyle beerStyle, Integer pageNumber, Integer pageSize);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDTO);

    void deleteBeer(UUID id);

    Optional<BeerDTO> patchBeer(UUID id, BeerDTO beerDTO);
}

```
## 005 Create Page Request Object

```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.mapper.BeerMapper;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceImplJPA implements BeerService {

    private final static Integer DEFAULT_PAGE_NUMBER = 1;
    private final static Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {

        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize != null && pageSize > 0) {
            if (pageSize > 100) {
                queryPageSize = 100;
            } else {
                queryPageSize = pageSize;
            }
        } else {
            queryPageSize = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(queryPageNumber, queryPageSize);

    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<BeerDTO> listAllBeers(String beerName, Boolean showInventory, BeerStyle beerStyle, Integer pageNumber, Integer pageSize) {
        List<Beer> beerList;

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerList = listBeersByName(beerName);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = listBeerNameAndStyle(beerName, beerStyle);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = getAllByBeerStyle(beerStyle);
        } else {
            beerList = beerRepository.findAll();
        }

        if (showInventory != null && !showInventory) {
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList.stream().map(beerMapper::beerToBeerDto).toList();
    }
```


## 006 Refactor Spring Data JPA Repositories

```java
package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
}

```
```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    Page<BeerDTO> listAllBeers(String beerName, Boolean showInventory, BeerStyle beerStyle, Integer pageNumber, Integer pageSize);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDTO);

    void deleteBeer(UUID id);

    Optional<BeerDTO> patchBeer(UUID id, BeerDTO beerDTO);
}

```
```java
    @Override
    public Page<BeerDTO> listAllBeers(String beerName, Boolean showInventory, BeerStyle beerStyle, Integer pageNumber, Integer pageSize) {
        Page<Beer> beerPage;

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeerNameAndStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = getAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    private Page<Beer> getAllByBeerStyle(BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    }

    private Page<Beer> listBeerNameAndStyle(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageRequest);
    }

    private Page<Beer> listBeersByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
    }

```
[github full change details](https://github.com/ccweerasinghe1994/SpringFramework6BeginnertoGuru/commit/94ca49ef613ad31b864af6e12abc4b1b242c1a03)

## 007 Add Sort Parameter

```java

```
