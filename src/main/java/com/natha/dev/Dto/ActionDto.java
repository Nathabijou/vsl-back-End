package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class ActionDto {
    private Long id;
    private int nombre;
    private BigDecimal montant;
    private LocalDateTime dateAchat;
    private String accountId;

}
