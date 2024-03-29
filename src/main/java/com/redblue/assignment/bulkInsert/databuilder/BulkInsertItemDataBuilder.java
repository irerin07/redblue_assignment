package com.redblue.assignment.bulkInsert.databuilder;

import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BulkInsertItemDataBuilder {
  private final Random random = new Random();
  private int total;

  public static BulkInsertItemDataBuilder builder(){
    return new BulkInsertItemDataBuilder();
  }

  public BulkInsertItemDataBuilder setTotal(int total){
    this.total = total;
    return this;
  }

  public List<BulkInsertItem> build(){
    //@formatter:off
    return IntStream.range(0, total)
      .mapToObj(val -> BulkInsertItem.builder()
        .name("Product " + val)
        .amount(random.nextInt(total))
        .description("bulk test")
        .build()
      ).collect(Collectors.toList());
    //@formatter:on
  }
}
