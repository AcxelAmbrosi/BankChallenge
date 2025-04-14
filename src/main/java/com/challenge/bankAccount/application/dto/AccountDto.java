package com.challenge.bankAccount.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String number;
    private String type;
    private BigDecimal initialBalance;
    private boolean active;
    private Long customerId;
    private BigDecimal currentBalance;
}
