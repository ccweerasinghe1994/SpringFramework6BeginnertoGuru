package com.wchamara.spring6di.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceConstructorInjectImpl implements GreetingService {
    /**
     * @return
     */
    @Override
    public String sayGreeting() {
        return "Hello From Constructor Injected Greeting Service";
    }
}
