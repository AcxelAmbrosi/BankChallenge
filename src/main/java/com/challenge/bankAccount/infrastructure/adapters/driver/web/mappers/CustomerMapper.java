package com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers;


import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.customer.CustomerCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.customer.CustomerResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers.common.GenderMapper;
import com.challenge.bankAccount.domain.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GenderMapper.class)
public interface CustomerMapper {
    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    Customer toDomain(CustomerCreateDto dto);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    CustomerResponseDto toDto(Customer customer);

}
