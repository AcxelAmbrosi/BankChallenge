package com.challenge.bankAccount.domain.ports.input;

import com.challenge.bankAccount.domain.models.AccountStatement;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ReportServicePort {
    Flux<AccountStatement> getReport(Long clientId, LocalDate startDate, LocalDate endDate);
}
