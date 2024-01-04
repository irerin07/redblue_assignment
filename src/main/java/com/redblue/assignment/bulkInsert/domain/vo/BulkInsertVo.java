package com.redblue.assignment.bulkInsert.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_ABSENT, content = JsonInclude.Include.NON_EMPTY)
public class BulkInsertVo {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Create {
    List<BulkInsertVo.Item> items;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Item {
    private String name;
    private String description;
    private Integer amount;

    public Item(String name, String description, Integer amount) {
      this.name = name;
      this.description = description;
      this.amount = amount;
    }

    public BulkInsertItem toEntity() {
      return BulkInsertItem.builder()
        .name(name)
        .description(description)
        .amount(amount)
        .build();
    }

  }

}
