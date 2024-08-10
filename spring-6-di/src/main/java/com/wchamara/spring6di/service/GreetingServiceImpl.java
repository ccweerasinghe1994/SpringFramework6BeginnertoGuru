package com.wchamara.spring6di.service;

public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello Every One From Base Greeting Service";
    }
}
