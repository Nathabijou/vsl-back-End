package com.natha.dev.IService;

import com.natha.dev.Dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface AccountISercive {

    Optional<AccountDto> findById(String accountId);
    List<AccountDto> findAll();
    void deleteById(String accountId);
    AccountDto save(AccountDto accountDto);
    List<AccountDto> findByUserName(String username);

    AccountDto createAccountForUserInGroup(String username, Long groupId, AccountDto dto);

    AccountDto toggleAccountStatus(String accountId, boolean active);
}
