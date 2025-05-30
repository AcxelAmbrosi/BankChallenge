package com.challenge.bankAccount.infrastructure.adapters.input.web.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String gender;
    private String identification;
    private String address;
    private String phoneNumber;
    private Boolean status;
}
