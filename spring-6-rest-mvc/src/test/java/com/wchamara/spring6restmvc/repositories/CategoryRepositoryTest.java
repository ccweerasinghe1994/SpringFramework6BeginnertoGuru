package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.entities.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testAddCategory() {

        Category testCategory = Category.builder().description("Ales").build();
        Category savedCategory = categoryRepository.save(testCategory);

        testBeer.addCategory(savedCategory);

        Beer savedBeer = beerRepository.save(testBeer);

        System.out.println(savedBeer.getCategories().size());
    }
}