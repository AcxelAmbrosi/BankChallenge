package com.challenge.bankAccount.domain.ports.output;

import com.challenge.bankAccount.domain.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementRepository {
    Flux<Movement> findAll();

    Mono<Movement> findById(Long id);

    Mono<Movement> save(Movement movement);

    Mono<Void> deleteById(Long id);

    Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
}
