package com.challenge.bankAccount.infrastructure.adapters.output.postgres.repositories;

import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.MovementEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface MovementR2dbcRepository extends R2dbcRepository<MovementEntity, Long> {
    Flux<MovementEntity> findByAccountIdAndDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
