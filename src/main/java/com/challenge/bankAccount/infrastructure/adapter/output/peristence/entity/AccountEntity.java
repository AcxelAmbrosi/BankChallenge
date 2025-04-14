package com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity;

import com.challenge.bankAccount.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("accounts")
public class AccountEntity {
    @Id
    private Long id;
    private String number;
    private AccountType type;
    private BigDecimal initialBalance;
    private Boolean status;
    private Long customerId;
    private BigDecimal currentBalance;
}