package com.wchamara.spring6di.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceSetterInjectImpl implements GreetingService {
    /**
     * @return
     */
    @Override
    public String sayGreeting() {
        return "Hello From Setter Injected Greeting Service";
    }
}
