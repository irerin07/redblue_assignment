package com.redblue.assignment.bulkInsert.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redblue.assignment.bulkInsert.service.BulkInsertService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BulkInsertController.RESOURCE_URI)
@RequiredArgsConstructor
public class BulkInsertController {

  static final String RESOURCE_URI = "/bulk";

  private final BulkInsertService service;

  @PostMapping(path = "/{size}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> create(@PathVariable(name = "size") int size) {
    service.set(size);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
