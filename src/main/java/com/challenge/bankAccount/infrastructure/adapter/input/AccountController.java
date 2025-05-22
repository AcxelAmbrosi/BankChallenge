package com.challenge.bankAccount.infrastructure.adapter.input;


import com.challenge.bankAccount.application.dto.AccountCreateDto;
import com.challenge.bankAccount.application.dto.AccountResponseDto;
import com.challenge.bankAccount.application.mapper.AccountMapper;
import com.challenge.bankAccount.application.ports.input.AccountUseCase;
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

    private final AccountUseCase accountUseCase;
    private final AccountMapper mapper;

    @GetMapping
    public Flux<AccountResponseDto> getAllAccounts() {
        log.info("REST request to get all accounts");
        return accountUseCase.getAllAccounts()
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<AccountResponseDto> getAccountById(@PathVariable Long id) {
        log.info("REST request to get account by id: {}", id);
        return accountUseCase.getAccountById(id)
                .map(mapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountResponseDto> createAccount(@Valid @RequestBody AccountCreateDto createDto) {
        log.info("REST request to create account: {}", createDto);
        return Mono.just(createDto)
                .map(mapper::toDomain)
                .flatMap(accountUseCase::createAccount)
                .map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<AccountResponseDto> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountCreateDto updateDto) {
        log.info("REST request to update account with id: {}", id);
        return Mono.just(updateDto)
                .map(mapper::toDomain)
                .flatMap(account -> accountUseCase.updateAccount(id, account))
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable Long id) {
        log.info("REST request to delete account with id: {}", id);
        return accountUseCase.deleteAccount(id);
    }
}
