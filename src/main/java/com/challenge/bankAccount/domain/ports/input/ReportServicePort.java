package com.challenge.bankAccount.domain.ports.input;

import com.challenge.bankAccount.domain.models.AccountStatement;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ReportServicePort {
    Mono<AccountStatement> generateAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate);
}
