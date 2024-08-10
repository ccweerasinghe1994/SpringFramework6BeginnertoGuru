package com.wchamara.spring6di;

import com.wchamara.spring6di.controller.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Test class for the Spring6DiApplication.
 */
@SpringBootTest
class Spring6DiApplicationTests {

    /**
     * The application context used for retrieving beans.
     */
    @Autowired
    private ApplicationContext context;

    /**
     * The MyController bean used for testing.
     */
    @Autowired
    private MyController myController;

    /**
     * Test method to verify that the MyController bean can be retrieved from the application context.
     */
    @Test
    void testGetControllerFromContext() {
        // Retrieve the MyController bean from the application context
        MyController myController = context.getBean(MyController.class);

        // Call the sayHello method of MyController and store the result
        String greeting = myController.sayHello();

        // Print the greeting to the console
        System.out.println(greeting);
    }

    /**
     * Test method to verify that the MyController bean can be autowired and used.
     */
    @Test
    void testGetControllerFromAutowired() {
        // Call the sayHello method of MyController and store the result
        String greeting = myController.sayHello();

        // Print the greeting to the console
        System.out.println(greeting);
    }
}