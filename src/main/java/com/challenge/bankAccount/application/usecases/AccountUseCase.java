package com.challenge.bankAccount.application.usecases;

import com.challenge.bankAccount.domain.ports.driver.AccountServicePort;
import com.challenge.bankAccount.domain.ports.driven.AccountRepository;
import com.challenge.bankAccount.domain.exceptions.ConflictException;
import com.challenge.bankAccount.domain.exceptions.NotFoundException;
import com.challenge.bankAccount.domain.models.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountUseCase implements AccountServicePort {

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
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Account not found with id: {}", id);
                    return Mono.error(new NotFoundException("Account not found with id: " + id));
                }))
                .doOnSuccess(exist -> log.info("Account found with ID: {}", id))
                .doOnError(e -> log.error("Error occurred while found account: {}", e.getMessage(), e));
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        log.info("Creating account: {}", account);
        return accountRepository.findByNumber(account.getNumber())
                .flatMap(existing -> {
                    log.warn("Conflict: Account already exists with number: {}", account.getNumber());
                    return Mono.<Account>error(new ConflictException("Account already exists with number: " + account.getNumber()));
                })
                .switchIfEmpty(Mono.defer(() -> accountRepository.save(account)
                        .doOnSuccess(saved -> log.info("Account successfully created with ID: {}", saved.getId()))
                        .doOnError(e -> log.error("Error occurred while saving account: {}", e.getMessage(), e))));
    }


    @Override
    public Mono<Account> updateAccount(Long id, Account account) {
        log.info("Updating account with id: {}", id);
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Account not found with id: {}", id);
                    return Mono.error(new NotFoundException("Account not found with id: " + id));
                }))
                .flatMap(existingAccount ->
                        accountRepository.findByNumber(account.getNumber())
                                .flatMap(duplicate -> {
                                    if (!duplicate.getId().equals(id)) {
                                        log.warn("Duplicate number detected: {}", account.getNumber());
                                        return Mono.error(new ConflictException("number already used by another account"));
                                    }
                                    account.setId(id);
                                    return accountRepository.save(account)
                                            .doOnSuccess(saved -> log.info("Account updated successfully with id: {}", saved.getId()));
                                })
                                .switchIfEmpty(
                                        Mono.defer(() -> {
                                            account.setId(id);
                                            return accountRepository.save(account)
                                                    .doOnSuccess(saved -> log.info("account updated successfully with id: {}", saved.getId()));
                                        })
                                )
                                .doOnError(e -> log.error("Failed to update account with id: {}. Error: {}", id, e.getMessage(), e))
                );
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        log.info("Deleting account with id: {}", id);
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Account not found with id: {}", id);
                    return Mono.error(new NotFoundException("Account not found with id: " + id));
                }))
                .flatMap(account -> accountRepository.deleteById(id)
                        .doOnSuccess(deleted -> log.info("Account deleted successfully with id: {}", id)))
                .doOnError(e -> log.error("Failed to delete account with id: {}. Error: {}", id, e.getMessage(), e));
    }
}