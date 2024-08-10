package com.wchamara.spring6di;

import com.wchamara.spring6di.controller.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The main application class for the Spring Boot application.
 */
@SpringBootApplication
public class Spring6DiApplication {

    /**
     * The main method which serves as the entry point for the Spring Boot application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // Start the Spring application and obtain the application context
        ConfigurableApplicationContext context = SpringApplication.run(Spring6DiApplication.class, args);

        // Retrieve the MyController bean from the application context
        MyController myController = context.getBean(MyController.class);

        // Call the sayHello method of MyController and store the result
        String greeting = myController.sayHello();

        // Print the greeting to the console
        System.out.println(greeting);
    }

}