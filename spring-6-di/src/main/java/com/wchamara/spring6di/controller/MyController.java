package com.wchamara.spring6di.controller;

import com.wchamara.spring6di.service.GreetingService;
import com.wchamara.spring6di.service.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

/**
 * Controller class that handles requests and returns responses.
 */
@Controller
public class MyController {


    /**
     * Represents a controller class that handles requests and returns responses.
     * This class is responsible for handling the business logic related to greetings.
     */
    private final GreetingService greetingService;

    /**
     * This class represents a controller that handles requests and returns responses.
     * It is responsible for managing the flow of data between the user interface and the business logic.
     */
    public MyController() {
        this.greetingService = new GreetingServiceImpl();
    }


    /**
     * Method that prints a message to the console and returns a greeting.
     *
     * @return A greeting message "Hello World!"
     */
    public String sayHello() {
        System.out.println("I am the Controller");
        return greetingService.sayGreeting();
    }

    public void beforeInit() {
        System.out.println("Before Init - Called by Bean Post Processor");
    }

    public void afterInit() {
        System.out.println("After Init - Called by Bean Post Processor");
    }
}