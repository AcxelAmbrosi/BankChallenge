package com.challenge.bankAccount.infrastructure.adapters.output.postgres.repositories;

import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CustomerR2dbcRepository extends R2dbcRepository<CustomerEntity, Long> {
    Mono<CustomerEntity> findByIdentification(String identification);
}
