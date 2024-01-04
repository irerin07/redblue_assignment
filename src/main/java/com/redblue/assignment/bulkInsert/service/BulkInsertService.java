package com.redblue.assignment.bulkInsert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redblue.assignment.bulkInsert.databuilder.BulkInsertItemDataBuilder;
import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;
import com.redblue.assignment.bulkInsert.repository.BulkInsertRepository;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BulkInsertService {

  private final HikariDataSource hikariDataSource;

  private final BulkInsertRepository repository;

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
  private int batchSize;

  @Transactional(readOnly = false)
  public void set(int size) {
    //@formatter:off
    List<BulkInsertItem> bulkInsertItems = BulkInsertItemDataBuilder
      .builder()
      .setTotal(size)
      .build();
    //@formatter:on

    subListInsert(bulkInsertItems);
  }

  public void subListInsert(List<BulkInsertItem> bulkInsertItems) {
    List<List<BulkInsertItem>> subList = createSubList(bulkInsertItems, 500);
    for (List<BulkInsertItem> insertItems : subList) {
      repository.saveAll(insertItems);
    }
  }

  public static <T> List<List<T>> createSubList(List<T> list, int subListSize) {
    List<List<T>> listOfSubList = new ArrayList<>();
    for (int i = 0; i < list.size(); i += subListSize) {
      if (i + subListSize <= list.size()) {
        listOfSubList.add(list.subList(i, i + subListSize));
      } else {
        listOfSubList.add(list.subList(i, list.size()));
      }
    }

    return listOfSubList;
  }

}
