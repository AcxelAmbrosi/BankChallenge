package com.challenge.bankAccount.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement {
    private Long clientId;
    private LocalDateTime reportGenerationDate;
    private List<Account> accounts;
    private Map<Long, List<Movement>> movementsByAccountId;
}