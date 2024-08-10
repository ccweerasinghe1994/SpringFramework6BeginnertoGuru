package com.wchamara.spring6di.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServicePropertyInject implements GreetingService {
    /**
     * @return
     */
    @Override
    public String sayGreeting() {
        return "Hello From Property Injected Greeting Service";
    }
}
