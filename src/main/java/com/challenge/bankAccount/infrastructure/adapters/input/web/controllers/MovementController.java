package com.challenge.bankAccount.infrastructure.adapters.input.web.controllers;

import com.challenge.bankAccount.domain.exceptions.NotFoundException;
import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.domain.ports.output.AccountRepository;
import com.challenge.bankAccount.domain.ports.input.MovementServicePort;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.movement.MovementCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.movement.MovementResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.MovementMapper;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.common.MovementTypeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movements")
public class MovementController {

    private final MovementServicePort movementServicePort;
    private final AccountRepository accountRepository;
    private final MovementMapper mapper;

    @GetMapping
    public Flux<MovementResponseDto> getAllMovements() {
        log.info("REST request to get all movements");
        return movementServicePort.getAllMovements()
                .map(mapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<MovementResponseDto> getMovementById(@PathVariable Long id) {
        log.info("REST request to get movement by id: {}", id);
        return movementServicePort.getMovementById(id)
                .map(mapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovementResponseDto> createMovement(@Valid @RequestBody MovementCreateDto createDto) {
        log.info("REST request to create movement: {}", createDto);
        return accountRepository.findByNumber(createDto.getNumber())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found with number: " + createDto.getNumber())))
                .map(account ->
                        Movement.builder()
                                .type(MovementTypeMapper.stringToMovementType(createDto.getType()))
                                .amount(createDto.getAmount())
                                .accountId(account.getId())
                                .date(LocalDateTime.now())
                                .build())
                .flatMap(movementServicePort::createMovement)
                .map(mapper::toDto);
    }

    @PutMapping("/{id}")
    public Mono<MovementResponseDto> updateMovement(@PathVariable Long id, @Valid @RequestBody MovementCreateDto createDto) {
        log.info("REST request to update movement with id: {}", id);
        return Mono.just(createDto)
                .map(mapper::toDomain)
                .flatMap(movement -> movementServicePort.updateMovement(id, movement))
                .map(mapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovement(@PathVariable Long id) {
        log.info("REST request to delete movement with id: {}", id);
        return movementServicePort.deleteMovement(id);
    }
}