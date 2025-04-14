package com.challenge.bankAccount.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends Person {
    private Long id;
    private String password;
    @Builder.Default
    private Boolean status = true;
}