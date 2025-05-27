package com.challenge.bankAccount.infrastructure.adapters.driver.web.dtos.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank
    @Pattern(regexp = "MALE|FEMALE", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Gender must be MALE or FEMALE")
    private String gender;

    @NotBlank
    @Pattern(regexp = "\\d{6,12}", message = "Identification must be numeric and between 6 and 12 digits")
    private String identification;

    @NotBlank
    @Size(min = 5, max = 100)
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits, optionally starting with +")
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 30)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
            message = "Password must include uppercase, lowercase, digit, and special character"
    )
    private String password;
}
