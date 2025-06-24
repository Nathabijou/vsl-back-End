package com.natha.dev.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComposanteDto {
    private Long id;
    private String code;
    @JsonProperty("name")
    private String nom;
    private String description;
    private String createdBy;
    private String type; // ZONE_BASED / CENTER_BASED / DIRECT_PROJECT
    private String applicationId;
}
