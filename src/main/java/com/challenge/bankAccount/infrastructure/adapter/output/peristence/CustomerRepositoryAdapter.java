package com.challenge.bankAccount.infrastructure.adapter.output.peristence;

import com.challenge.bankAccount.application.ports.outputs.CustomerRepository;
import com.challenge.bankAccount.domain.model.Customer;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.CustomerEntity;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.mapper.CustomerPersistenceMapper;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.repository.CustomerR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerR2dbcRepository repository;
    private final CustomerPersistenceMapper mapper;

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
}