package com.challenge.bankAccount.application.ports.input;

import com.challenge.bankAccount.domain.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountUseCase {
    Flux<Account> getAllAccounts();

    Mono<Account> getAccountById(Long id);

    Mono<Account> createAccount(Account account);

    Mono<Account> updateAccount(Long id, Account account);

    Mono<Void> deleteAccount(Long id);
}