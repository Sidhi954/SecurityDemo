package com.bank.service;


import com.bank.dto.AccountRequestDTO;
import com.bank.dto.AccountResponseDTO;
import java.util.List;

public interface AccountService {
    AccountResponseDTO createAccount(AccountRequestDTO request);
    AccountResponseDTO getAccountById(Long id);
    List<AccountResponseDTO> getAccountByUserId(Long userId);
}
