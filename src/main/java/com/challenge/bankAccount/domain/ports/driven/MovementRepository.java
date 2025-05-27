package com.challenge.bankAccount.domain.ports.driven;

import com.challenge.bankAccount.domain.models.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Flux<Movement> findAll();

    Mono<Movement> findById(Long id);

    Mono<Movement> save(Movement movement);

    Mono<Void> deleteById(Long id);

}
