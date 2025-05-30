package com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private String number;
    private String type;
    private BigDecimal currentBalance;
    private Boolean status;
    private Long customerId;
}
