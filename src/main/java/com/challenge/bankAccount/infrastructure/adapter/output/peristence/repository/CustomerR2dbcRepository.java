package com.challenge.bankAccount.infrastructure.adapter.output.peristence.repository;

import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerR2dbcRepository extends R2dbcRepository<CustomerEntity, Long> {
}
