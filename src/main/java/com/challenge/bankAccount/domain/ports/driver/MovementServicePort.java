package com.challenge.bankAccount.domain.ports.driver;

import com.challenge.bankAccount.domain.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementServicePort {
    Flux<Movement> getAllMovements();

    Mono<Movement> getMovementById(Long id);

    Mono<Movement> createMovement(Movement movement);

    Mono<Movement> updateMovement(Long id, Movement movement);

    Mono<Void> deleteMovement(Long id);
//
//    Mono<Movement> makeDeposit(Long accountId, BigDecimal amount);
//
//    Mono<Movement> makeWithdrawal(Long accountId, BigDecimal amount);
}