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
        dto.setNumberOfShares(deposit.getNumberOfShares());
        dto.setDescription(deposit.getDescription());
        dto.setCreateBy(deposit.getCreateBy()); 
        dto.setLastModifiedBy(deposit.getLastModifiedBy());
        dto.setLastModifiedDate(deposit.getLastModifiedDate());
        if (deposit.getAccount() != null) {
            dto.setAccountId(deposit.getAccount().getId());
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
        deposit.setNumberOfShares(dto.getNumberOfShares());
        deposit.setDescription(dto.getDescription());
        deposit.setCreateBy(dto.getCreateBy()); 
        deposit.setLastModifiedBy(dto.getLastModifiedBy());
        deposit.setLastModifiedDate(dto.getLastModifiedDate());
        // Note: Account will be set in the service layer
        return deposit;
    }
}
