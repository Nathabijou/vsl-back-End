package com.natha.dev.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositRequest {
    private Long depositId;
    private BigDecimal amount;
    private LocalDateTime depositDate;
}
