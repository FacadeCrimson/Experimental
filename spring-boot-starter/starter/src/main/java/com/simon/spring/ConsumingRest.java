package com.simon.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

public class ConsumingRest {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Quote {
    private final String type;
    private final Value value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Value {
    private final Long id;
    private final String quote;
    }

    private static final Logger log = LoggerFactory.getLogger(ConsumingRest.class);

    // @Bean
	// public RestTemplate restTemplate(RestTemplateBuilder builder) {
	// 	return builder.build();
	// }


	@Bean
    @Scheduled(fixedRate = 5000)
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString()+dateFormat.format(new Date()));
		};
	}

    public static void main(String[] args) {
        SpringApplication.run(ConsumingRest.class, args);
    }
}
