package com.challenge.bankAccount.infrastructure.adapters.input.web.controllers;


import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.account.AccountCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.account.AccountResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.AccountMapper;
import com.challenge.bankAccount.domain.ports.input.AccountServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountServicePort accountServicePort;
    private final AccountMapper mapper;

    @GetMapping
    public Flux<AccountResponseDto> getAllAccounts() {
        log.info("REST request to get all accounts");
        return accountServicePort.getAllAccounts()
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<AccountResponseDto> getAccountById(@PathVariable Long id) {
        log.info("REST request to get account by id: {}", id);
        return accountServicePort.getAccountById(id)
                .map(mapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountResponseDto> createAccount(@Valid @RequestBody AccountCreateDto createDto) {
        log.info("REST request to create account: {}", createDto);
        return Mono.just(createDto)
                .map(mapper::toDomain)
                .flatMap(accountServicePort::createAccount)
                .map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<AccountResponseDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountCreateDto updateDto) {
        log.info("REST request to update account with id: {}", id);
        return Mono.just(updateDto)
                .map(mapper::toDomain)
                .flatMap(account -> accountServicePort.updateAccount(id, account))
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        log.info("REST request to delete account with id: {}", id);
        return accountServicePort.deleteAccount(id);
    }
}
