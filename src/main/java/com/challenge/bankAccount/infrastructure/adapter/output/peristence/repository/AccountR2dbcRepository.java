package com.challenge.bankAccount.infrastructure.adapter.output.peristence.repository;

import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface AccountR2dbcRepository extends R2dbcRepository<AccountEntity, Long> {
    Mono<AccountEntity> findByNumber(String number);
}
