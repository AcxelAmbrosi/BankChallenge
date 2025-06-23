package com.challenge.bankAccount.application.usecases;

import com.challenge.bankAccount.domain.enums.MovementType;
import com.challenge.bankAccount.domain.exceptions.InsufficientBalanceException;
import com.challenge.bankAccount.domain.exceptions.InvalidAmountException;
import com.challenge.bankAccount.domain.exceptions.NotFoundException;
import com.challenge.bankAccount.domain.models.Account;
import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.domain.ports.output.AccountRepository;
import com.challenge.bankAccount.domain.ports.output.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovementUseCaseTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MovementUseCase movementUseCase;

    @Test
    void createMovement_shouldCreateCreditMovementSuccessfully() {
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.valueOf(100));
        movement.setType(MovementType.CREDIT);
        movement.setAccountId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setCurrentBalance(BigDecimal.valueOf(200));

        when(accountRepository.findById(1L)).thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class))).thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.createMovement(movement))
                .expectNextMatches(result -> result.getBalance().equals(BigDecimal.valueOf(300)))
                .verifyComplete();

        verify(movementRepository).save(any(Movement.class));
    }

    @Test
    void createMovement_shouldFailForNegativeAmount() {
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.valueOf(-10));
        movement.setType(MovementType.CREDIT);

        StepVerifier.create(movementUseCase.createMovement(movement))
                .expectError(InvalidAmountException.class)
                .verify();
    }

    @Test
    void createMovement_shouldFailForMissingType() {
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.valueOf(100));

        StepVerifier.create(movementUseCase.createMovement(movement))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void createMovement_shouldFailIfAccountNotFound() {
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.valueOf(50));
        movement.setType(MovementType.DEBIT);
        movement.setAccountId(99L);

        when(accountRepository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(movementUseCase.createMovement(movement))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void createMovement_shouldFailForInsufficientBalanceOnDebit() {
        Movement movement = new Movement();
        movement.setAmount(BigDecimal.valueOf(500));
        movement.setType(MovementType.DEBIT);
        movement.setAccountId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setCurrentBalance(BigDecimal.valueOf(200));

        when(accountRepository.findById(1L)).thenReturn(Mono.just(account));

        StepVerifier.create(movementUseCase.createMovement(movement))
                .expectError(InsufficientBalanceException.class)
                .verify();
    }
}