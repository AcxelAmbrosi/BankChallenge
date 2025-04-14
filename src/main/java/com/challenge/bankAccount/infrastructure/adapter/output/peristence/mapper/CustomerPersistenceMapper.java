package com.challenge.bankAccount.infrastructure.adapter.output.peristence.mapper;


import com.challenge.bankAccount.domain.model.Customer;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {
    Customer toDomain(CustomerEntity entity);

    CustomerEntity toEntity(Customer domain);
}