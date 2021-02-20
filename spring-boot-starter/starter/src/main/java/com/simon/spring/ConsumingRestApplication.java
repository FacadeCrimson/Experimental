package com.simon.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@SpringBootApplication
@EnableScheduling
public class ConsumingRestApplication {
    private static User user;
    private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private final Integer id;
        private final String name;
        private final String username;
        private final String email;
        private final Address address;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private final String street;
        private final String suite;
        private final String city;
        private final String zipcode;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            user = restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/1", User.class);
        };
    }

    @Scheduled(fixedRate = 5000)
    public void logging() {
        if (user != null) {
            log.info(user.toString() + dateFormat.format(new Date()));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRestApplication.class, args);
    }
}
