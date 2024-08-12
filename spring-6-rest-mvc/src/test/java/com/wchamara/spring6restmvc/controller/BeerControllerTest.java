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