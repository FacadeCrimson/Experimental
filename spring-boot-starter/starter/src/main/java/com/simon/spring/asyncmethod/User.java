package com.simon.spring.asyncmethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

  private String name;
  private String blog;

  @Override
  public String toString() {
    return "User [name=" + name + ", blog=" + blog + "]";
  }

}