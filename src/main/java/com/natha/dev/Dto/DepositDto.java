package com.natha.dev.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositDto {
    private String id;
    private BigDecimal amount;
    private LocalDateTime depositDate;
    private String accountId;
}
