package com.challenge.bankAccount.application.mapper;


import com.challenge.bankAccount.application.dto.CustomerCreateDto;
import com.challenge.bankAccount.application.dto.CustomerResponseDto;
import com.challenge.bankAccount.domain.model.Customer;
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
