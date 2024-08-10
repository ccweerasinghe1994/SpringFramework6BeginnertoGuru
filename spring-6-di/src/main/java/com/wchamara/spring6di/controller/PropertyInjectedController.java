package com.wchamara.spring6di.controller;

import com.wchamara.spring6di.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyInjectedController {

    @Autowired
    @Qualifier("greetingServicePropertyInject")
    public GreetingService greetingService;

    public String sayGreeting() {
        return greetingService.sayGreeting();
    }

}
