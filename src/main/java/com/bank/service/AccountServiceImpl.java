package com.bank.service;

import com.bank.dto.AccountRequestDTO;
import com.bank.dto.AccountResponseDTO;
import com.bank.entity.Account;
import com.bank.entity.User;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.UserNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl  implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        User user=userRepository.findById(request.getUserId())
                .orElseThrow(()-> new UserNotFoundException("User not found with id: "+request.getUserId()));

        String accountNumber=generateAccountNumber();

        Account account= Account.builder()
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .balance(request.getInitialDeposit())
                .status(Account.AccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Account saved=accountRepository.save(account);
        return mapToResponse(saved);
    }

    private AccountResponseDTO mapToResponse(Account account) {
        AccountResponseDTO accountResponseDTO=new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setAccountNumber(account.getAccountNumber());
        accountResponseDTO.setAccountType(account.getAccountType().name());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setStatus(account.getStatus().name());
        accountResponseDTO.setCreatedDate(account.getCreatedAt());
        accountResponseDTO.setUserId(account.getUser().getId());
        accountResponseDTO.setUserName(account.getUser().getFullName());
        return accountResponseDTO;
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        Account account=accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFoundException("Account not found with id: " +id));
        return mapToResponse(account);
    }

    @Override
    public List<AccountResponseDTO> getAccountByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: " +userId));

        List<Account> accounts=accountRepository.findByUserId(userId);
        return accounts.stream().map(this::mapToResponse).toList();
    }

    private String generateAccountNumber() {
        String uuid= UUID.randomUUID().toString().replace("-","").substring(0,8);
        String accountNumber="ACC-"+uuid.toUpperCase();

        while(accountRepository.existsByAccountNumber(accountNumber)){
            uuid=UUID.randomUUID().toString().replace("-","").substring(0,8);
            accountNumber="ACC-"+uuid.toUpperCase();
        }

        return accountNumber;
    }
}
