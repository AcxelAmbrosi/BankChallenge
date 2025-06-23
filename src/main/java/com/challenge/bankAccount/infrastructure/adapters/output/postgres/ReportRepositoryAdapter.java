package com.challenge.bankAccount.infrastructure.adapters.output.postgres;

import com.challenge.bankAccount.domain.models.AccountStatement;
import com.challenge.bankAccount.domain.ports.output.AccountStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryAdapter implements AccountStatementRepository {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<AccountStatement> getReport(LocalDate startDate, LocalDate endDate, Long clientId) {
        String sql = """
                SELECT
                    m.date AS fecha,
                    c.name AS cliente,
                    a.number AS numeroCuenta,
                    a.type AS tipoCuenta,
                    a.initial_balance AS saldoInicialCuenta,
                    a.status AS estadoCuenta,
                    m.amount AS valorMovimiento,
                    m.type AS tipoMovimiento,
                    m.balance AS saldoDisponible
                FROM movements m
                JOIN accounts a ON m.account_id = a.id
                JOIN customers c ON a.customer_id = c.id
                WHERE m.date BETWEEN :startDate AND :endDate
                  AND a.customer_id = :clientId
                ORDER BY m.date ASC;
                """;

        return databaseClient.sql(sql)
                .bind("startDate", startDate)
                .bind("endDate", endDate)
                .bind("clientId", clientId)
                .map((row, metadata) -> AccountStatement.builder()
                        .date(row.get("fecha", LocalDateTime.class))
                        .customer(row.get("cliente", String.class))
                        .accountNumber(row.get("numeroCuenta", String.class))
                        .accountType(row.get("tipoCuenta", String.class))
                        .initialAccountBalance(row.get("saldoInicialCuenta", BigDecimal.class))
                        .accountStatus(row.get("estadoCuenta", Boolean.class))
                        .movementAmount(row.get("valorMovimiento", BigDecimal.class))
                        .movementType(row.get("tipoMovimiento", String.class))
                        .availableBalance(row.get("saldoDisponible", BigDecimal.class))
                        .build())
                .all();
    }
}

