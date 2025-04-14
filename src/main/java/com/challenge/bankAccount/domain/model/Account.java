package com.challenge.bankAccount.domain.model;

import com.challenge.bankAccount.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String number;
    private AccountType type;
    private BigDecimal initialBalance;
    @Builder.Default
    private Boolean status = true;
    private Long customerId;
    private BigDecimal currentBalance;
}