package com.natha.dev.IService;

import com.natha.dev.Dto.DepositDto;

import java.util.List;

public interface IDepositService {
    DepositDto makeDeposit(String username, Long groupId, DepositDto depositDto);
    
    List<DepositDto> getDepositsByAccount(String accountId);
}
