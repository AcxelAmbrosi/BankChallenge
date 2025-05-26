package com.challenge.bankAccount.application.mapper;

import com.challenge.bankAccount.application.dto.account.AccountCreateDto;
import com.challenge.bankAccount.application.dto.account.AccountResponseDto;
import com.challenge.bankAccount.application.mapper.common.AccountTypeMapper;
import com.challenge.bankAccount.domain.model.Account;
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