package com.challenge.bankAccount.infrastructure.adapter.output.peristence.entity;

import com.challenge.bankAccount.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("customers")
public class CustomerEntity {
    @Id
    private Long id;
    private String name;
    private Gender gender;
    private String identification;
    private String address;
    private String phoneNumber;
    private String password;
    private Boolean status;
}