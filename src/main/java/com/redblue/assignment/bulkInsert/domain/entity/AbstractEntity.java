package com.redblue.assignment.bulkInsert.domain.entity;

import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@EqualsAndHashCode(callSuper = false, of = {"seq"})
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = -5785158509172208133L;

  /**
   * 일련번호
   */
  @Id
  @GenericGenerator(
    name = "SequenceGenerator",
    parameters = {
      @Parameter(name = "sequence_name", value = "hibernate_sequence"),
      @Parameter(name = "optimizer", value = "pooled"),
      @Parameter(name = "initial_value", value = "1"),
      @Parameter(name = "increment_size", value = "1000")
    }
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "SequenceGenerator"
  )
  @Column(name = "seq")
  protected Long seq;

}
