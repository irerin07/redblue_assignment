package com.redblue.assignment.bulkInsert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redblue.assignment.bulkInsert.databuilder.BulkInsertItemDataBuilder;
import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;
import com.redblue.assignment.bulkInsert.repository.BulkInsertRepository;

import lombok.RequiredArgsConstructor;

/*
  JPA에서 bulk insert를 처리하기 위해서는 Entity의 수정부터 여러가지 설정 변경이 필요한 것 같다.
  예를 들어 Entity의 @Id의 GeneratedValue의 값을 IDENTITY를 사용할 시 INSERT 문이 실행되기 전에는 새로 할당된 값을 알 수 없기 때문에 transactional write-behind에 위배된다.
  그런 이유에서 IDENTITY를 SEQUENCE로 바꿔주고 기타 여러가지 설정을 변경했다.
  SEQUENCE 역시 increment_size 옵션을 1000으로 주었는데 테스트를 통해 적정값을 찾아 사용하면 좋을 것 같다.

  JDBC의 방식으로도 시도 해 보았지만 우선은 JPA로 구현하는 것이 과제에 나온 내용이었으므로 JPA만으로 구현해 보았다.

  동시성 이슈도 발생할 수 있을 것으로 예상 되는데 이런 경우엔 애플리케이션에서 처리하기가 쉽지 않을 것 같기도 하다. 미리 책정한 시퀀스 값을 사용한다 하더라도 중복이 발생할 수도 있지 않을까?
  이런 경우엔 DB자체에서 해결을 하는 방법이 있을 것 같다.

 */
@Service
@RequiredArgsConstructor
public class BulkInsertService {

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
