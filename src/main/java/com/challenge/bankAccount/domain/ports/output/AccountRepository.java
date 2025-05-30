package com.challenge.bankAccount.domain.ports.output;

import com.challenge.bankAccount.domain.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Flux<Account> findAll();

    Mono<Account> findById(Long id);

    Mono<Account> save(Account account);

    Mono<Void> deleteById(Long id);

    Mono<Account> findByNumber(String number);

    Flux<Account> findByCustomerId(Long customerId);
}