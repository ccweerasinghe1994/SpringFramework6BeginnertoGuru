package com.wchamara.spring6restmvc;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private String beerStyle;
    private Long upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
