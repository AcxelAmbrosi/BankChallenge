package com.challenge.bankAccount.infrastructure.adapter.output.peristence.mapper;


import com.challenge.bankAccount.domain.model.Account;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    Account toDomain(AccountEntity entity);

    AccountEntity toEntity(Account domain);
}
