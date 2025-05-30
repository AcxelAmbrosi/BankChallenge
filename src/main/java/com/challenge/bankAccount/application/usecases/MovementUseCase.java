package com.challenge.bankAccount.application.usecases;

import com.challenge.bankAccount.domain.enums.MovementType;
import com.challenge.bankAccount.domain.exceptions.InsufficientBalanceException;
import com.challenge.bankAccount.domain.exceptions.InvalidAmountException;
import com.challenge.bankAccount.domain.exceptions.NotFoundException;
import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.domain.ports.output.AccountRepository;
import com.challenge.bankAccount.domain.ports.output.MovementRepository;
import com.challenge.bankAccount.domain.ports.input.MovementServicePort;
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
public class MovementUseCase implements MovementServicePort {

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
                .switchIfEmpty(Mono.defer(() ->
                {
                    log.warn("Movement not found with id: {}", id);
                    return Mono.error(new NotFoundException("Movement not found with id: " + id));
                }))
                .doOnSuccess(exist -> log.info("Movement found with ID: {}", id))
                .doOnError(e -> log.error("Error occurred while found movement: {}", e.getMessage(), e));
    }

    public Mono<Movement> createMovement(Movement movement) {
        log.info("Starting to create movement: {}", movement);

        return validateMovement(movement)
                .flatMap(valid -> {
                    log.info("Movement validated successfully: {}", movement);
                    return processMovement(movement);
                })
                .doOnError(error -> log.error("Error during movement creation: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("Movement creation process completed"));
    }

    private Mono<Movement> validateMovement(Movement movement) {
        log.info("Validating movement: {}", movement);

        if (movement.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid movement amount: {}. Amount must be greater than zero.", movement.getAmount());
            return Mono.error(new InvalidAmountException("Movement amount must be greater than zero"));
        }

        if (movement.getType() == null) {
            log.warn("Missing movement type for: {}", movement);
            return Mono.error(new IllegalArgumentException("Movement type is required"));
        }

        log.info("Movement is valid: {}", movement);
        return Mono.just(movement);
    }

    private Mono<Movement> processMovement(Movement movement) {

        log.info("Processing movement with amount: {} and type: {}", movement.getAmount(), movement.getType());
        movement.setDate(LocalDateTime.now());

        return accountRepository.findById(movement.getAccountId())
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Account not found with id: {}", movement.getAccountId());
                    return Mono.error(new NotFoundException("Account not found with id: " + movement.getAccountId()));
                }))
                .flatMap(account -> {

                    BigDecimal newBalance = switch (movement.getType()) {
                        case CREDIT -> account.getCurrentBalance().add(movement.getAmount());
                        case DEBIT -> {
                            if (account.getCurrentBalance().compareTo(movement.getAmount()) < 0) {
                                log.error("Insufficient balance: {}. Cannot process debit of amount: {}", account.getCurrentBalance(), movement.getAmount());
                                throw new InsufficientBalanceException("Insufficient balance");
                            }
                            yield account.getCurrentBalance().subtract(movement.getAmount());
                        }
                        default -> {
                            log.error("Invalid movement type: {}", movement.getType());
                            throw new IllegalArgumentException("Invalid movement type. Must be CREDIT or DEBIT");
                        }
                    };
                    log.info("Calculated new balance for account: {}", newBalance);
                    movement.setBalance(newBalance);
                    account.setCurrentBalance(newBalance);

                    return accountRepository.save(account)
                            .doOnSuccess(savedAccount -> log.info("Account balance updated successfully: {}", savedAccount))
                            .then(movementRepository.save(movement))
                            .doOnSuccess(savedMovement -> log.info("Movement saved successfully: {}", savedMovement));
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