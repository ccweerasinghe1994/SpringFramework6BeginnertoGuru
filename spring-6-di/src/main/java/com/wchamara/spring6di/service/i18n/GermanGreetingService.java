package com.wchamara.spring6di.service.i18n;

import com.wchamara.spring6di.service.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("DE")
@Service("i18nService")
public class GermanGreetingService implements GreetingService {
    /**
     * @return
     */
    @Override
    public String sayGreeting() {
        return "Hallo Jeder von Deutscher Grußdienst";
    }
}
