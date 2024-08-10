package com.wchamara.spring6di.controller;

import com.wchamara.spring6di.service.GreetingService;

public class ConstructorInjectedController {

    GreetingService greetingService;

    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayGreeting() {
        return greetingService.sayGreeting();
    }

}
