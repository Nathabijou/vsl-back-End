package com.natha.dev.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositDto {
    private String id;

    private BigDecimal amount;
    
    private LocalDateTime depositDate;
    private Long accountId;
    private Integer numberOfShares;
    private String description;
    private String createBy;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}