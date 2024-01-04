package com.redblue.assignment.bulkInsert.domain.dto;

import java.io.Serial;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"seq"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AbstractDto implements Serializable {

  @Serial
  private static final long serialVersionUID = -713764464634092034L;


  /**
   * 일련번호
   */
  private Long seq;

}
