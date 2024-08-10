package com.wchamara.spring6di.controller;

import org.springframework.stereotype.Controller;

/**
 * Controller class that handles requests and returns responses.
 */
@Controller
public class MyController {

    /**
     * Method that prints a message to the console and returns a greeting.
     *
     * @return A greeting message "Hello World!"
     */
    public String sayHello() {
        System.out.println("I am the Controller");
        return "Hello World!";
    }

}