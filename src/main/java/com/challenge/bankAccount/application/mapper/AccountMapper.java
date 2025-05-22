package com.challenge.bankAccount.application.mapper;

import com.challenge.bankAccount.application.dto.AccountDto;
import com.challenge.bankAccount.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);

    Account toDomain(AccountDto dto);

}