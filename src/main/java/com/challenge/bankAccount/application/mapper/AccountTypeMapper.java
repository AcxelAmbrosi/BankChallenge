package com.challenge.bankAccount.application.mapper;

import com.challenge.bankAccount.domain.enums.AccountType;
import org.mapstruct.Named;

public class AccountTypeMapper {

    @Named("stringToAccountType")
    public static AccountType stringToAccountType(String type) {
        if (type == null) return null;
        try {
            return AccountType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid account type: " + type);
        }
    }

    @Named("accountTypeToString")
    public static String accountTypeToString(AccountType type) {
        return type != null ? type.name() : null;
    }
}
