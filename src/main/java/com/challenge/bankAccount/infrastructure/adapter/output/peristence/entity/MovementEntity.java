package com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity;

import com.challenge.bankAccount.domain.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("movements")
public class MovementEntity {
    @Id
    private Long id;
    private LocalDateTime date;
    private MovementType type;
    private BigDecimal amount;
    private BigDecimal balance;
    private Long accountId;
}