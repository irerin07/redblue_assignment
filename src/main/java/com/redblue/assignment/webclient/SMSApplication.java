package com.redblue.assignment.webclient;

import com.redblue.assignment.postmultipart.PostMultiPartAssignmentApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author 민경수
 * @description smsapplication
 * @since 2024.01.03
 **********************************************************************************************************************/
@SpringBootApplication
public class SMSApplication {

  public static void main(String[] args) {
    SpringApplication.run(SMSApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
