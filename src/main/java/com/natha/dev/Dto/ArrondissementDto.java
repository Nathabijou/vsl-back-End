package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArrondissementDto {
    private Long id;
    private String name;
    private Long departementId;
}
