package com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report;

import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.account.AccountResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.movement.MovementResponseDto;
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
public class AccountStatementResponse {
    private Long clientId;
    private LocalDateTime reportGenerationDate;
    private List<AccountResponseDto> accounts;
    private Map<Long, List<MovementResponseDto>> movementsByAccountId;
}