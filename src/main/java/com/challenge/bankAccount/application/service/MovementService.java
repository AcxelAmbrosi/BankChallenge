package com.challenge.bankAccount.application.service;

import com.challenge.bankAccount.application.ports.input.MovementUseCase;
import com.challenge.bankAccount.application.ports.outputs.AccountRepository;
import com.challenge.bankAccount.application.ports.outputs.MovementRepository;
import com.challenge.bankAccount.domain.enums.MovementType;
import com.challenge.bankAccount.domain.exception.InsufficientBalanceException;
import com.challenge.bankAccount.domain.exception.InvalidAmountException;
import com.challenge.bankAccount.domain.exception.NotFoundException;
import com.challenge.bankAccount.domain.model.Movement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService implements MovementUseCase {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Override
    public Flux<Movement> getAllMovements() {
        log.info("Getting all movements");
        return movementRepository.findAll();
    }

    @Override
    public Mono<Movement> getMovementById(Long id) {
        log.info("Getting movement with id: {}", id);
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Movement not found with id: " + id)));
    }

    @Override
    public Mono<Movement> createMovement(Movement movement) {
        log.info("Creating movement: {}", movement);

        if (movement.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new InvalidAmountException("Movement amount must be greater than zero"));
        }

        movement.setDate(LocalDateTime.now());

        return accountRepository.findById(movement.getAccountId())
                .switchIfEmpty(Mono.error(new NotFoundException("Account not found with id: " + movement.getAccountId())))
                .flatMap(account -> {
                    BigDecimal newBalance;

                    if (movement.getType() == MovementType.CREDIT) {
                        newBalance = account.getCurrentBalance().add(movement.getAmount());
                    } else if (movement.getType() == MovementType.DEBIT) {
                        if (account.getCurrentBalance().compareTo(movement.getAmount()) < 0) {
                            return Mono.error(new InsufficientBalanceException("Insufficient balance"));
                        }
                        newBalance = account.getCurrentBalance().subtract(movement.getAmount());
                    } else {
                        return Mono.error(new IllegalArgumentException("Invalid movement type. Must be CREDIT or DEBIT"));
                    }

                    movement.setBalance(newBalance);

                    account.setCurrentBalance(newBalance);

                    return accountRepository.save(account)
                            .then(movementRepository.save(movement));
                });
    }

    @Override
    public Mono<Movement> updateMovement(Long id, Movement movement) {
        log.info("Updating movement with id: {}", id);
        return Mono.error(new UnsupportedOperationException("Movement update is not supported"));
    }

    @Override
    public Mono<Void> deleteMovement(Long id) {
        log.info("Deleting movement with id: {}", id);
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Movement not found with id: " + id)))
                .flatMap(movement -> {
                    return accountRepository.findById(movement.getAccountId())
                            .flatMap(account -> {
                                BigDecimal updatedBalance;
                                if (movement.getType() == MovementType.CREDIT) {
                                    updatedBalance = account.getCurrentBalance().subtract(movement.getAmount());
                                } else {
                                    updatedBalance = account.getCurrentBalance().add(movement.getAmount());
                                }

                                account.setCurrentBalance(updatedBalance);
                                return accountRepository.save(account);
                            })
                            .then(movementRepository.deleteById(id));
                });
    }
}