package com.natha.dev.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommuneDto {
    private Long id;
    private String nom;
    private Long departementId;
}