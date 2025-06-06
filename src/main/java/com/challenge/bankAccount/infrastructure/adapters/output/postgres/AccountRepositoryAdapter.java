package com.challenge.bankAccount.infrastructure.adapters.output.postgres;

import com.challenge.bankAccount.domain.models.Account;
import com.challenge.bankAccount.domain.ports.output.AccountRepository;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.AccountEntity;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.mappers.AccountEntityMapper;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.repositories.AccountR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountR2dbcRepository repository;
    private final AccountEntityMapper mapper;

    @Override
    public Flux<Account> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Account> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Account> save(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        return repository.save(entity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Account> findByNumber(String number) {
        return repository.findByNumber(number)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Account> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId)
                .map(mapper::toDomain);
    }
}
