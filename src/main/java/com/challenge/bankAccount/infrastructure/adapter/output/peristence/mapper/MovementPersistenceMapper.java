package com.challenge.bankAccount.infrastructure.adapter.output.peristence.mapper;

import com.challenge.bankAccount.domain.model.Movement;
import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.MovementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementPersistenceMapper {
    Movement toDomain(MovementEntity entity);

    MovementEntity toEntity(Movement domain);
}