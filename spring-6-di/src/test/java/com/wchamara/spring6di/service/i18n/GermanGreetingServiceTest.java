package com.wchamara.spring6di.service.i18n;

import com.wchamara.spring6di.controller.MyI18nController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("EN")
@SpringBootTest
class GermanGreetingServiceTest {

    @Autowired
    MyI18nController myI18nController;

    @Test
    void getGreeting() {
        System.out.println(myI18nController.sayHello());
    }
}