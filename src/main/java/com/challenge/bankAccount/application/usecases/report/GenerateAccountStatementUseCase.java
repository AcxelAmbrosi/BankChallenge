package com.challenge.bankAccount.application.usecases.report;

import com.challenge.bankAccount.domain.models.AccountStatement;
import com.challenge.bankAccount.domain.ports.input.ReportServicePort;
import com.challenge.bankAccount.domain.ports.output.AccountStatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateAccountStatementUseCase implements ReportServicePort {

    private final AccountStatementRepository repository;


    @Override
    public Flux<AccountStatement> getReport(Long clientId, LocalDate startDate, LocalDate endDate) {
        return repository.getReport(startDate, endDate, clientId);
    }
}
