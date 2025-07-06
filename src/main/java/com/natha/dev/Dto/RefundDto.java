package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefundDto {
    private Long id;

    private BigDecimal amountRefunded;

    private LocalDate refundDate;
}
