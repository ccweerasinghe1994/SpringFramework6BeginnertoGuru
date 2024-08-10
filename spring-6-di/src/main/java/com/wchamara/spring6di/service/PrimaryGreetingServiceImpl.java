package com.wchamara.spring6di.service;

import org.springframework.stereotype.Service;

@Service
public class PrimaryGreetingServiceImpl implements GreetingService {
    /**
     * @return
     */
    @Override
    public String sayGreeting() {
        return "Hello From Primary Greeting Service";
    }
}
