package com.challenge.bankAccount.infrastructure.adapters.driven.postgres.mappers;


import com.challenge.bankAccount.domain.models.Account;
import com.challenge.bankAccount.infrastructure.adapters.driven.postgres.entities.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountEntityMapper {
    Account toDomain(AccountEntity entity);

    AccountEntity toEntity(Account domain);
}
