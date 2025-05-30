package com.challenge.bankAccount.domain.ports.output;

import com.challenge.bankAccount.domain.models.AccountStatement;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface AccountStatementRepository {
    Flux<AccountStatement> getReport(LocalDate startDate, LocalDate endDate, Long clientId);
}
