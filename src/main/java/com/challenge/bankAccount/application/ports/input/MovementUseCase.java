package com.challenge.bankAccount.application.ports.input;

import com.challenge.bankAccount.domain.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementUseCase {
    Flux<Movement> getAllMovements();

    Mono<Movement> getMovementById(Long id);

    Mono<Movement> createMovement(Movement movement);

    Mono<Movement> updateMovement(Long id, Movement movement);

    Mono<Void> deleteMovement(Long id);
}