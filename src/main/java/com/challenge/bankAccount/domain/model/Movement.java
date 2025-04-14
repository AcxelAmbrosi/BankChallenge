package com.challenge.bankAccount.domain.model;

import com.challenge.bankAccount.domain.enums.MovementType;
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
public class Movement {
    private Long id;
    private LocalDateTime date;
    private MovementType type;
    private BigDecimal amount;
    private BigDecimal balance;
    private Long accountId;
}