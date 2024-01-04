package com.redblue.assignment.bulkInsert.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;

public class ItemDto extends AbstractDto {

  @Serial
  private static final long serialVersionUID = -4613528480329912645L;


  private Long seq;

  private String name;

  private String description;

  private Integer amount;

  public ItemDto(Long seq, String name, String description, Integer amount) {
    this.seq = seq;
    this.name = name;
    this.description = description;
    this.amount = amount;
  }

}
