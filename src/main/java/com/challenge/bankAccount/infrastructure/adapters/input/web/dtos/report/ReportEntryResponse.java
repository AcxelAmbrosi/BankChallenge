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
    private LocalDateTime date;
    private String customer;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialAccountBalance;
    private Boolean accountStatus;
    private BigDecimal movementAmount;
    private String movementType;
    private BigDecimal availableBalance;

}