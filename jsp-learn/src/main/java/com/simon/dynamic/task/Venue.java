package com.simon.dynamic.task;


import lombok.Data;

@Data
public class Venue {
    private final int id;
    private final String name;
    private final String address;
    private final String city;
    private final String state;
    private final String phone;
    private final String website;
    private final String genres;
}