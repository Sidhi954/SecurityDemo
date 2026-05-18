package com.bank.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponseDTO {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;
    private String status;
    private LocalDateTime createdDate;
    private Long userId;
    private String userName;
}
