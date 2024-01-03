package com.redblue.assignment.bulkInsert.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redblue.assignment.bulkInsert.databuilder.BulkInsertItemDataBuilder;
import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;
import com.redblue.assignment.bulkInsert.repository.BulkInsertRepository;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.Table;
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
    List<BulkInsertItem> bulkInsertItems = BulkInsertItemDataBuilder
      .builder()
      .setTotal(size)
      .build();


    //    repository.saveAll(bulkInsertItems);
    //    saveAllJdbcBatchCallable(bulkInsertItems);
    //    jdbcBatchInsert(bulkInsertItems);
    subListInsert(bulkInsertItems);
  }

  public void subListInsert(List<BulkInsertItem> bulkInsertItems) {
    List<List<BulkInsertItem>> subList = createSubList(bulkInsertItems, 500);
    for (List<BulkInsertItem> insertItems : subList) {
      repository.saveAll(insertItems);
    }
  }

  public void saveAllJdbcBatchCallable(List<BulkInsertItem> productData) {
    ExecutorService executorService = Executors.newFixedThreadPool(hikariDataSource.getMaximumPoolSize());

    List<Callable<Void>> callables = createSubList(productData, batchSize).stream()
      .map(sublist -> (Callable<Void>) () -> {
        jdbcBatchInsert(sublist);
        return null;
      })
      .collect(Collectors.toList());

    try {
      executorService.invokeAll(callables);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void jdbcBatchInsert(List<BulkInsertItem> bulkInsertItems) {
    String sql = String.format("INSERT INTO %s (name, description, amount) VALUES (?, ?, ?)", BulkInsertItem.class.getAnnotation(Table.class).name());

    try (Connection connection = hikariDataSource.getConnection();
      PreparedStatement statement = connection.prepareStatement(sql)) {
      int counter = 0;

      for (BulkInsertItem product : bulkInsertItems) {
        statement.clearParameters();
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setInt(3, product.getAmount());
        statement.addBatch();

        if ((counter + 1) % batchSize == 0 || (counter + 1) == bulkInsertItems.size()) {
          statement.executeBatch();
          statement.clearBatch();
        }

        counter++;
      }

    } catch (Exception e) {
      e.printStackTrace();
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
