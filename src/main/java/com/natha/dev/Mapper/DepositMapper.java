package com.natha.dev.Mapper;

import com.natha.dev.Dto.DepositDto;
import com.natha.dev.Model.Deposit;
import org.springframework.stereotype.Component;

@Component
public class DepositMapper {
    
    public DepositDto toDto(Deposit deposit) {
        if (deposit == null) {
            return null;
        }
        
        DepositDto dto = new DepositDto();
        dto.setId(deposit.getId());
        dto.setAmount(deposit.getAmount());
        dto.setDepositDate(deposit.getDepositDate());
        if (deposit.getAccount() != null) {
            dto.setAccountId(String.valueOf(deposit.getAccount().getId()));
        }
        return dto;
    }
    
    public Deposit toEntity(DepositDto dto) {
        if (dto == null) {
            return null;
        }
        
        Deposit deposit = new Deposit();
        deposit.setId(dto.getId());
        deposit.setAmount(dto.getAmount());
        deposit.setDepositDate(dto.getDepositDate());
        // Note: Account will be set in the service layer
        return deposit;
    }
}
