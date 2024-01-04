package com.redblue.assignment.postmultipart.controller;

import com.redblue.assignment.postmultipart.domain.vo.FileVo;

import com.redblue.assignment.postmultipart.service.ImageFileService;

import java.io.IOException;

import java.nio.file.Path;

import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FileController.RESOURCE_URI)
@RequiredArgsConstructor
public class FileController {

  static final String RESOURCE_URI = "/files";

  private final ImageFileService service;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> create(@Validated @ModelAttribute FileVo.Create create) {
    service.set(create);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
  public ResponseEntity<String> read(@RequestParam(name = "fileName") String fileName) {
    return new ResponseEntity<>(service.get(fileName), HttpStatus.OK);
  }

}
