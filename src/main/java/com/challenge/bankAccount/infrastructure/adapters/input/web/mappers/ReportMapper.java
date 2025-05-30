package com.challenge.bankAccount.infrastructure.adapters.input.web.mappers;

import com.challenge.bankAccount.domain.models.AccountStatement;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report.AccountStatementResponse;
import com.challenge.bankAccount.infrastructure.adapters.input.web.mappers.common.MovementListMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {AccountMapper.class, MovementListMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReportMapper {

    @Mapping(source = "accounts", target = "accounts")
    @Mapping(source = "movementsByAccountId", target = "movementsByAccountId", qualifiedByName = "mapMovementsByAccountId")
    AccountStatementResponse toResponse(AccountStatement domain);
}
