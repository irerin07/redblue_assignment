package com.redblue.assignment.bulkInsert.controller;

import com.redblue.assignment.bulkInsert.domain.vo.BulkInsertVo;

import com.redblue.assignment.bulkInsert.service.BulkInsertService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * @author 민경수
 * @description bulk insert controller
 * @since 2024.01.02
 **********************************************************************************************************************/
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
