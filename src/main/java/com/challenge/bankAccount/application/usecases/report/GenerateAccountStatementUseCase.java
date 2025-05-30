package com.challenge.bankAccount.application.usecases.report;

import com.challenge.bankAccount.domain.models.Account;
import com.challenge.bankAccount.domain.models.AccountStatement;
import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.domain.ports.output.AccountRepository;
import com.challenge.bankAccount.domain.ports.output.MovementRepository;
import com.challenge.bankAccount.domain.ports.input.ReportServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateAccountStatementUseCase implements ReportServicePort {

    private final AccountRepository accountRepository;
    private final MovementRepository repository;

    @Override
    public Mono<AccountStatement> generateAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return accountRepository.findByCustomerId(clientId)
                .collectList()
                .flatMap(accounts -> {
                    if (accounts.isEmpty()) {
                        log.warn("No accounts found for client ID: {}", clientId);
                        return Mono.error(new IllegalArgumentException("No accounts found for client ID: " + clientId));
                    }

                    return getMovementsByAccount(accounts, startDate, endDate)
                            .map(movementsByAccountId -> AccountStatement.builder()
                                    .clientId(clientId)
                                    .reportGenerationDate(LocalDateTime.now())
                                    .accounts(accounts)
                                    .movementsByAccountId(movementsByAccountId)
                                    .build()
                            );
                });
    }

    private Mono<Map<Long, List<Movement>>> getMovementsByAccount(List<Account> accounts, LocalDateTime startDate, LocalDateTime endDate) {
        return Flux.fromIterable(accounts)
                .flatMap(account ->
                        repository.findByAccountIdAndDateBetween(account.getId(), startDate, endDate)
                                .collectList()
                                .map(movements -> Map.entry(account.getId(), movements))
                )
                .collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }

}
