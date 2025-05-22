package com.challenge.bankAccount.application.mapper;


import com.challenge.bankAccount.application.dto.MovementDto;
import com.challenge.bankAccount.domain.model.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementDto toDto(Movement movement);

    Movement toDomain(MovementDto dto);

}
