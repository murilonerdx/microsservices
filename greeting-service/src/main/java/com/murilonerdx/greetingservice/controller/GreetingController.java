package com.murilonerdx.greetingservice.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.murilonerdx.greetingservice.config.GreetingConfiguration;
import com.murilonerdx.greetingservice.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    final
    GreetingConfiguration configuration;

    public GreetingController(GreetingConfiguration configuration) {
        this.configuration = configuration;
    }

    @RequestMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value="name",
                    defaultValue = "World") String name) {

        if(name.isEmpty()) name = configuration.getDefaultValue();
        return new Greeting(
                counter.incrementAndGet(),
                String.format(template, configuration.getGreeting(), name)
        );
    }
}
