package com.challenge.bankAccount.infrastructure.adapter.output.peristence;

import com.challenge.bankAccount.application.ports.outputs.MovementRepository;
import com.challenge.bankAccount.domain.model.Movement;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.MovementEntity;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.mapper.MovementPersistenceMapper;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.repository.MovementR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepository {

    private final MovementR2dbcRepository repository;
    private final MovementPersistenceMapper mapper;

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

}
