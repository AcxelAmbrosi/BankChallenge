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
                        .fecha(row.get("fecha", LocalDateTime.class))
                        .cliente(row.get("cliente", String.class))
                        .numeroCuenta(row.get("numeroCuenta", String.class))
                        .tipoCuenta(row.get("tipoCuenta", String.class))
                        .saldoInicialCuenta(row.get("saldoInicialCuenta", BigDecimal.class))
                        .estadoCuenta(row.get("estadoCuenta", Boolean.class))
                        .valorMovimiento(row.get("valorMovimiento", BigDecimal.class))
                        .tipoMovimiento(row.get("tipoMovimiento", String.class))
                        .saldoDisponible(row.get("saldoDisponible", BigDecimal.class))
                        .build())
                .all();
    }
}

