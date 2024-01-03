package com.redblue.assignment.bulkInsert.domain.entity;

import jakarta.persistence.Entity;

import java.io.Serial;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 민경수
 * @description item
 * @since 2024.01.02
 **********************************************************************************************************************/
@Getter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "tb_bulk_insert_item")
public class BulkInsertItem extends AbstractEntity {

  @Serial
  private static final long serialVersionUID = -7164496416159297330L;


  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "amount")
  private Integer amount;

  @Builder
  public BulkInsertItem(String name, String description, Integer amount) {
    this.name = name;
    this.description = description;
    this.amount = amount;
  }

}
