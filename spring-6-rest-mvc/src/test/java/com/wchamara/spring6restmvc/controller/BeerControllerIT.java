package com.wchamara.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.UUID;

import static com.wchamara.spring6restmvc.controller.BeerControllerTest.JWT_REQUEST_POST_PROCESSOR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;
    @Autowired
    ObjectMapper objectMapper;
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @Test
    void testListOfYoursWithPagination() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(JWT_REQUEST_POST_PROCESSOR)
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "TRUE")
                                .queryParam("pageNumber", "2")
                                .queryParam("pageSize", "50")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(50))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isNotEmpty())
        ;
    }

    @Test
    void getBeerByName() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(JWT_REQUEST_POST_PROCESSOR)
                                .queryParam("beerName", "IPA")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25));
//                .andExpect(result -> {
//                    String content = result.getResponse().getContentAsString();
//                    List<BeerDTO> beerDTOS = objectMapper.readValue(content, List.class);
//                    assertThat(beerDTOS.size()).isEqualTo(336);
//                });

    }

    @Test
    void getBeerByNameQueryParameter() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(JWT_REQUEST_POST_PROCESSOR)
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "TRUE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isNotEmpty())
        ;


    }

    @Test
    void getBeerByNameQueryParameterAndShowInventoryFalse() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(JWT_REQUEST_POST_PROCESSOR)
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "FALSE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isEmpty())
        ;


    }


    @Test
    void testListAllBeers() {
        Page<BeerDTO> beerDTOS = beerController.listAllBeers(null, false, null, 25, 1);

        assertEquals(25, beerDTOS.getContent().size());
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        Page<BeerDTO> beerDTOS = beerController.listAllBeers(null, false, null, 25, 1);
        assertThat(beerDTOS.getContent().size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void beerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testNewBeerSuccess() {

        BeerDTO newBeer1 = BeerDTO.builder().beerName("New Beer").build();
        ResponseEntity responseEntity = beerController.saveNewBeer(newBeer1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().get("Location")).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(location[location.length - 1]);

        Beer savedBeer = beerRepository.findById(savedId).get();
        assertThat(savedBeer.getBeerName()).isEqualTo("New Beer");
        assertThat(savedBeer.getId()).isEqualTo(savedId);

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateBeer() {

        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        beerDTO.setBeerName("Updated Beer");
        ResponseEntity responseEntity = beerController.updateBeer(beerDTO.getId(), beerDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beerDTO.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void testUpdateBeerNotFound() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Updated Beer").build();
        assertThrows(NotFoundException.class, () -> beerController.updateBeer(UUID.randomUUID(), beerDTO));
    }

    @Test
    void testDeleteBeer() {
        UUID id = beerRepository.findAll().get(0).getId();
        ResponseEntity responseEntity = beerController.deleteBeer(id);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(id)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteBeerNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.deleteBeer(UUID.randomUUID()));
    }


    @Test
    void updateBeerReturnsNoContent() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = Map.of(
                "beerName", "New Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer Name",
                "beerStyle", "New Beer Style",
                "price", 12.99,
                "quantityOnHand", 100
        );


        MvcResult mvcResult = mockMvc.perform(
                        patch(BeerController.BEER_PATH_ID, beer.getId())
                                .with(JWT_REQUEST_POST_PROCESSOR)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


}