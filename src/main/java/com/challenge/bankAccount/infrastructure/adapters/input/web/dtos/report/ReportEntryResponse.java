package com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntryResponse {
    private LocalDateTime fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicialCuenta;
    private Boolean estadoCuenta;
    private BigDecimal valorMovimiento;
    private String tipoMovimiento;
    private BigDecimal saldoDisponible;
}