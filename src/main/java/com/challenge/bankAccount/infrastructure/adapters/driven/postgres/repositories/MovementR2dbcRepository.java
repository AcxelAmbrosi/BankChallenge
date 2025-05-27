package com.challenge.bankAccount.infrastructure.adapters.driven.postgres.repositories;

import com.challenge.bankAccount.infrastructure.adapters.driven.postgres.entities.MovementEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MovementR2dbcRepository extends R2dbcRepository<MovementEntity, Long> {
}
