package com.challenge.bankAccount.infrastructure.adapters.output.postgres.mappers;


import com.challenge.bankAccount.domain.models.Customer;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer domain);
}