package com.challenge.bankAccount.infrastructure.adapters.driven.postgres;

import com.challenge.bankAccount.domain.ports.driven.CustomerRepository;
import com.challenge.bankAccount.domain.models.Customer;
import com.challenge.bankAccount.infrastructure.adapters.driven.postgres.entities.CustomerEntity;
import com.challenge.bankAccount.infrastructure.adapters.driven.postgres.mappers.CustomerEntityMapper;
import com.challenge.bankAccount.infrastructure.adapters.driven.postgres.repositories.CustomerR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerR2dbcRepository repository;
    private final CustomerEntityMapper mapper;

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        return repository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Customer> findByIdentification(String identification) {
        return repository.findByIdentification(identification).map(mapper::toDomain);
    }
}