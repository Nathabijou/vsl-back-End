package com.natha.dev.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDto {
    private Long id;
    private String nom;
    private Long composanteId; // Pou fè lyezon an
}
