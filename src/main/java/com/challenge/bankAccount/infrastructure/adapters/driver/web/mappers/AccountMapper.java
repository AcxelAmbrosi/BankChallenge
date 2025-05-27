package com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers;

import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.account.AccountCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.account.AccountResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers.common.AccountTypeMapper;
import com.challenge.bankAccount.domain.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountTypeMapper.class)
public interface AccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currentBalance", source = "initialBalance")
    @Mapping(target = "status", constant = "true")
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToAccountType")
    Account toDomain(AccountCreateDto dto);

    @Mapping(source = "type", target = "type", qualifiedByName = "accountTypeToString")
    AccountResponseDto toDto(Account account);
}