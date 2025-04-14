package com.challenge.bankAccount.application.mapper;


import com.challenge.bankAccount.application.dto.CustomerDto;
import com.challenge.bankAccount.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);

    Customer toDomain(CustomerDto dto);

    void updateCustomerFromDto(CustomerDto dto, @MappingTarget Customer customer);
}
