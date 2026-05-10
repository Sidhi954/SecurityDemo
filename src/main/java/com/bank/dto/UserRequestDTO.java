package com.bank.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {

    @NotBlank(message="Full name is required")
    private String fullName;

    @Email(message="Enter a valid email")
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message="Address cannot be empty")
    private String address;

    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dateOfBirth;
}
