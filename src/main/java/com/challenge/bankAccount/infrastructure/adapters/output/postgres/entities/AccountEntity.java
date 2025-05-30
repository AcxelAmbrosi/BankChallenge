package com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    private String type;
    @Column("initial_balance")
    private BigDecimal initialBalance;
    @Builder.Default
    private Boolean status = true;
    @Column("customer_id")
    private Long customerId;
    @Column("current_balance")
    private BigDecimal currentBalance;
}