package com.natha.dev.IService;

import com.natha.dev.Dto.DepositDto;

import java.util.List;
import java.util.Map;

public interface IDepositService {
    DepositDto makeDeposit(String username, Long groupId, DepositDto depositDto);
    
    Map<String, Object> getDepositsByAccount(String accountId);
    
    Map<String, Object> getDepositsByUserAndGroup(String username, Long groupId);
    
    DepositDto updateDeposit(String depositId, DepositDto depositDto, String modifiedBy);
    
    void deleteDeposit(String id);
}
