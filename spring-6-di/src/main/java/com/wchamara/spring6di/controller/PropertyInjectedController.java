package com.wchamara.spring6di.controller;

import com.wchamara.spring6di.service.GreetingService;

public class PropertyInjectedController {

    public GreetingService greetingService;

    public String sayGreeting() {
        return greetingService.sayGreeting();
    }

}
