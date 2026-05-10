package com.bank.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String status;

}
