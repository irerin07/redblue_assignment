package com.redblue.assignment.webclient.controller;

import com.redblue.assignment.webclient.domain.vo.SMSVo;

import com.redblue.assignment.webclient.service.SMSSenderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SMSController.RESOURCE_URI)
@RequiredArgsConstructor
public class SMSController {

  static final String RESOURCE_URI = "/sms";

  private final SMSSenderService service;

  @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> read() {
    service.webClientSMSSender("token");
    service.restSMSSender("token");

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SMSVo.Response> create(@RequestBody SMSVo.Create create) {

    return ResponseEntity.ok(SMSVo.Response.toVo());
  }


}
