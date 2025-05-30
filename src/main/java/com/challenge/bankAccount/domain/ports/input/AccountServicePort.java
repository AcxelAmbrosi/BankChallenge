package com.challenge.bankAccount.domain.ports.input;

import com.challenge.bankAccount.domain.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountServicePort {
    Flux<Account> getAllAccounts();

    Mono<Account> getAccountById(Long id);

    Mono<Account> createAccount(Account account);

    Mono<Account> updateAccount(Long id, Account account);

    Mono<Void> deleteAccount(Long id);
}