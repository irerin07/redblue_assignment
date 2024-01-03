package com.redblue.assignment.bulkInsert.repository;

import com.redblue.assignment.bulkInsert.domain.entity.BulkInsertItem;

import com.redblue.assignment.bulkInsert.repository.querydsl.BulkInsertRepositoryQueryDsl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkInsertRepository extends JpaRepository<BulkInsertItem, Long>, BulkInsertRepositoryQueryDsl {
}
