package com.challenge.bankAccount.infrastructure.adapters.output.postgres.mappers;

import com.challenge.bankAccount.domain.models.Movement;
import com.challenge.bankAccount.infrastructure.adapters.output.postgres.entities.MovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementEntityMapper {
    Movement toDomain(MovementEntity entity);

    MovementEntity toEntity(Movement domain);
}