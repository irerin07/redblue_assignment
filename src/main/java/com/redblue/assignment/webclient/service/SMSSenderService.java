package com.redblue.assignment.webclient.service;

import com.redblue.assignment.webclient.domain.vo.SMSVo;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

/*
  RestTemplate와 WebClient 각각의 configuration 클래스를 생성해 사용할 수도 있지만 우선은 그냥 구현했다.
 */
@Service
public class SMSSenderService {

  private final WebClient webClient;
  private final RestTemplate restTemplate;

  public SMSSenderService(WebClient.Builder webClientBuilder, RestTemplate restTemplate) {
    this.webClient = webClientBuilder.baseUrl("https://{hostname}/api/sendSMS").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

    this.restTemplate = restTemplate;
  }

  public void restSMSSender(String token) {
    String uri = UriComponentsBuilder.fromUriString("https://{hostname}/api/sendSMS").toUriString();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-type", "application/json;charset=UTF-8");
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    httpHeaders.setBearerAuth(token);

    // @formatter:off
    SMSVo.Request request = SMSVo.Request.builder()
      .title("안녕하세요")
      .content("안녕하세요! SMS 샘플 테스트입니다.")
      .targetPhoneNumber("+82-10-1234-1234")
      .build();
    // @formatter:on

    ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), String.class);
    String body = exchange.getBody();
  }

  public void webClientSMSSender(String token) {
    // @formatter:off
    SMSVo.Request request = SMSVo.Request.builder()
      .title("안녕하세요")
      .content("안녕하세요! SMS 샘플 테스트입니다.")
      .targetPhoneNumber("+82-10-1234-1234")
      .build();
    // @formatter:on

    // @formatter:off
    Mono<String> stringMono = webClient.post()
      .header("Authorization", "Bearer " + token)
      .accept(MediaType.APPLICATION_JSON)
      .body(BodyInserters.fromValue(request))
      .retrieve()
      .bodyToMono(String.class);
    // @formatter:on

    String block = stringMono.block();
  }

}
