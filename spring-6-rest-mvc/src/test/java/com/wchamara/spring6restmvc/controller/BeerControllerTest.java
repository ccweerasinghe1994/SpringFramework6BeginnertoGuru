package com.wchamara.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.service.BeerService;
import com.wchamara.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerByIdReturnsBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));


        mockMvc.perform(
                get(BeerController.BEER_PATH_ID, beerDTO.getId())
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(beerDTO.getId().toString()))
                .andExpect(jsonPath("$.beerName").value(beerDTO.getBeerName()))
                .andExpect(jsonPath("$.beerStyle").value(beerDTO.getBeerStyle().toString()))
                .andExpect(jsonPath("$.upc").value(beerDTO.getUpc()))
                .andExpect(jsonPath("$.quantityOnHand").value(beerDTO.getQuantityOnHand()))
                .andExpect(jsonPath("$.price").value(beerDTO.getPrice().toString()))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.updatedDate").exists());
        ;
    }


    @Test
    void getBeerByIdWillReturnNotFoundException() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());


        mockMvc.perform(
                get(BeerController.BEER_PATH_ID, UUID.randomUUID())
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void listAllBeersReturnsListOfBeers() throws Exception {
        given(beerService.listAllBeers(any(), any(), any(), any(), any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25));

        mockMvc.perform(
                get(
                        BeerController.BEER_PATH)
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].beerName").exists())
                .andExpect(jsonPath("$.content[0].beerStyle").exists())
                .andExpect(jsonPath("$.content[0].upc").exists())
                .andExpect(jsonPath("$.content[0].quantityOnHand").exists())
                .andExpect(jsonPath("$.content[0].price").exists())
                .andExpect(jsonPath("$.content[0].createdDate").exists())
                .andExpect(jsonPath("$.content[0].updatedDate").exists());
    }

    @Test
    void saveNewBeerReturnsCreated() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        given(beerService.saveNewBeer(any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(1));

        mockMvc.perform(
                        post(BeerController.BEER_PATH)
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void testCreateNewBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();


        given(beerService.saveNewBeer(any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(1));

        ResultActions resultActions = mockMvc.perform(
                        post(BeerController.BEER_PATH)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isBadRequest());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());

    }

    @Test
    void updateBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.updateBeer(any(), any())).willReturn(Optional.of(beerDTO));


        mockMvc.perform(
                        put(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isNoContent());
        verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void deleteBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beerDTO.getId()))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beerDTO.getId());
    }

    @Test
    void patchBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);

        Map<String, Object> beerMap = new HashMap<>();

        beerMap.put("beerName", "New Beer Name");

        mockMvc.perform(
                        patch(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isNotFound());

//        verify(beerService).patchBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
//        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beerDTO.getId());
//        assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo("New Beer Name");

    }


}