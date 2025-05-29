package com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers;


import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.movement.MovementCreateDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.movement.MovementResponseDto;
import com.challenge.bankAccount.infrastructure.adapters.driver.web.mappers.common.MovementTypeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MovementTypeMapper.class)
public interface MovementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToMovementType")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "accountId", ignore = true )
    Movement toDomain(MovementCreateDto dto);

    @Mapping(source = "type", target = "type", qualifiedByName = "movementTypeToString")
    MovementResponseDto toDto(Movement movement);
}
