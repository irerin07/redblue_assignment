package com.redblue.assignment.bulkInsert.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

/**
 * @author 민경수
 * @description item
 * @since 2024.01.02
 **********************************************************************************************************************/
public class ItemDto implements Serializable {

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
