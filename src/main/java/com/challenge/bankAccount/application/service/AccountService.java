package com.challenge.bankAccount.application.service;

import com.challenge.bankAccount.application.ports.input.AccountUseCase;
import com.challenge.bankAccount.application.ports.outputs.AccountRepository;
import com.challenge.bankAccount.domain.exception.NotFoundException;
import com.challenge.bankAccount.domain.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> getAllAccounts() {
        log.info("Getting all accounts");
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> getAccountById(Long id) {
        log.info("Getting account with id: {}", id);
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found with id: " + id)));
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        log.info("Creating account: {}", account);
        account.setCurrentBalance(account.getInitialBalance());
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateAccount(Long id, Account account) {
        log.info("Updating account with id: {}", id);
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found with id: " + id)))
                .flatMap(existingAccount -> {
                    account.setId(id);
                    if (account.getCurrentBalance() == null) {
                        account.setCurrentBalance(existingAccount.getCurrentBalance());
                    }
                    return accountRepository.save(account);
                });
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        log.info("Deleting account with id: {}", id);
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found with id: " + id)))
                .flatMap(account -> accountRepository.deleteById(id));
    }
}