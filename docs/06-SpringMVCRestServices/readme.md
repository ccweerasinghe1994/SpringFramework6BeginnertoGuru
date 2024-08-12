# 06 - Spring MVC Rest Services

## 001 Introduction
![alt text](image.png)
## 002 Introducing SFG Beer Works
![alt text](image-1.png)
## 003 HTTP GET with Spring MVC List Operation
```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.model.BeerStyle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap = new HashMap<>();

    public BeerServiceImpl() {
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(BigDecimal.valueOf(12.95))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Dog")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123457")
                .price(BigDecimal.valueOf(12.95))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Fish")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123458")
                .price(BigDecimal.valueOf(12.95))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);


    }

    @Override
    public Beer getBeerById(UUID id) {
        return beerMap.get(id);
    }

    @Override
    public List<Beer> listAllBeers() {
        return new ArrayList<>(beerMap.values());
    }
}

```
```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);

    List<Beer> listAllBeers();
}

```
```java
package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.model.Beer;
import com.wchamara.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {
        log.debug("getBeerById() called in BeerController with id: {}", id);
        return beerService.getBeerById(id);
    }

    @RequestMapping("api/v1/beer")
    public List<Beer> listAllBeers() {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers();
    }
}

```
```java

```


## 004 HTTP Client
## 005 Using Path Parameters - Get By Id
## 006 Spring Boot Development Tools
## 007 HTTP POST with Spring MVC
## 008 Set Header on HTTP Response
## 009 HTTP PUT with Spring MVC
## 010 HTTP DELETE with Spring MVC
## 011 HTTP PATCH with Spring MVC
