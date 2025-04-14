package com.challenge.bankAccount.domain.model;

import com.challenge.bankAccount.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private Gender gender;
    private String identification;
    private String address;
    private String phoneNumber;
}