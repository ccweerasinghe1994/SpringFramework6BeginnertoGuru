# 09 - Spring Data JPA with Spring MVC

## 001 Introduction
![alt text](image.png)
## 002 Data Transfer Objects
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)

## 003 Refactoring to DTOs
```java
package com.wchamara.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

```
```java
package com.wchamara.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Data
@Builder
public class CustomerDTO {

    private String name;
    private UUID id;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
```
```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listAllBeers();

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeer(UUID id, BeerDTO beerDTO);

    void deleteBeer(UUID id);

    void patchBeer(UUID id, BeerDTO beerDTO);
}

```
```java

```
```java

```
```java

```

## 004 Spring Data JPA Dependencies


## 005 Creating JPA Entities


## 006 Hibernate UUID Id Generation


## 007 Spring Data JPA Repositories


## 008 Spring Boot JPA Test Splice


## 009 MapStruct Dependencies and Configuration


## 010 MapStruct Mappers


## 011 JPA Services


## 012 JPA Get Operations


## 013 Controller Integration Test


## 014 Testing for expected Exceptions


## 015 JPA Save New Beer Operation


## 016 JPA Update Beer by Id Operation


## 017 JPA Update Beer Not Found


## 018 JPA Delete Beer by Id


## 019 JPA Delete by Id Not Found