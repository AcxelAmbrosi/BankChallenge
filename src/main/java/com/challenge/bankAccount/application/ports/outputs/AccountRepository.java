package com.challenge.bankAccount.application.ports.outputs;

import com.challenge.bankAccount.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Flux<Account> findAll();

    Mono<Account> findById(Long id);

    Mono<Account> save(Account account);

    Mono<Void> deleteById(Long id);

    Mono<Account> findByNumber(String number);
}