package com.challenge.bankAccount.infrastructure.adapters.output.postgres;

import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.domain.ports.output.MovementRepository;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.MovementEntity;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.mappers.MovementEntityMapper;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.repositories.MovementR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepository {

    private final MovementR2dbcRepository repository;
    private final MovementEntityMapper mapper;

    @Override
    public Flux<Movement> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Movement> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        MovementEntity entity = mapper.toEntity(movement);
        return repository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    public Flux<Movement> findByAccountIdAndDateBetween(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByAccountIdAndDateBetween(accountId, startDate, endDate)
                .map(mapper::toDomain);
    }
}