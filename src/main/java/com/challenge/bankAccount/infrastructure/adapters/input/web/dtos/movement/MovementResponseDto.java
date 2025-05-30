package com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.movement;

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
public class MovementResponseDto {
    private Long id;
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
    private Long accountId;
}
