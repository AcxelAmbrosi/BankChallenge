package com.challenge.bankAccount.domain.models;

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
public class AccountStatement {
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