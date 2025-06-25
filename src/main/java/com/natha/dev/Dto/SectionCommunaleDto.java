package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionCommunaleDto {
    private Long id;
    private String name;
    private Long communeId;
}
