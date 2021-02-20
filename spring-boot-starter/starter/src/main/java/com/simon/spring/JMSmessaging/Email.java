package com.simon.spring.JMSmessaging;

import lombok.Data;

@Data
public class Email {
    private final String to;
    private final String body;
}