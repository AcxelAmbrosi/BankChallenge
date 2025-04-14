package com.challenge.bankAccount.infrastructure.adapter.output.peristence.repository;

import com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity.MovementEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MovementR2dbcRepository extends R2dbcRepository<MovementEntity, Long> {
}
