# 07 - Spring MockMVC Test with Mockito

## 001 Introduction
![alt text](image.png)
## 002 Introduction to Testing with MockMVC
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)
![alt text](image-6.png)
![alt text](image-7.png)
![alt text](image-8.png)
![alt text](image-9.png)
![alt text](image-10.png)
![alt text](image-11.png)
![alt text](image-12.png)
![alt text](image-13.png)
![alt text](image-14.png)
## 003 MockMVC Configuration
```java
package com.wchamara.spring6restmvc.controller;

import com.wchamara.spring6restmvc.service.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerByIdReturnsBeer() throws Exception {
        UUID beerId = UUID.fromString("60501fcd-487e-4d83-8c67-3001482e35a2");
        String url = "/api/v1/beer/" + beerId;
        mockMvc.perform(get(url).accept("application/json"))
                .andExpect(status().isOk());
    }


}
```
## 004 Return Data With Mockito
## 005 Using JSON Matchers
## 006 MockMVC Test List Beers
## 007 Create JSON Using Jackson
## 008 MockMVC Test Create Beer
## 009 MockMVC Test Update Beer
## 010 MockMVC Test Delete Beer
## 011 MockMVC Test Patch Beer
## 012 DRY - Don't Repeat Yourself
## 013 DRY - Refactoring
## 014 URI Builder