package com.challenge.bankAccount.infrastructure.adapters.driven.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    private String gender;
    private String identification;
    private String address;
    @Column("phone_number")
    private String phoneNumber;
    private String password;
    @Builder.Default
    private Boolean status = true;
}