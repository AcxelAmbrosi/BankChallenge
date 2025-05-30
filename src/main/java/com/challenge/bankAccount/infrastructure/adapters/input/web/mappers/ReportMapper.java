package com.challenge.bankAccount.infrastructure.adapters.input.web.mappers;

import com.challenge.bankAccount.domain.models.AccountStatement;
import com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.report.ReportEntryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    ReportEntryResponse toDto(AccountStatement accountStatement);
}

