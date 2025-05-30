package com.challenge.bankAccount.infrastructure.adapters.output.postgres.repositories;

import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountR2dbcRepository extends R2dbcRepository<AccountEntity, Long> {
    Mono<AccountEntity> findByNumber(String number);
    Flux<AccountEntity> findByCustomerId(Long customerId);
}
