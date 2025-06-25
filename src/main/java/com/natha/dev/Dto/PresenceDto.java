package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDto {
    private String idPresence;
    private LocalDate datePresence;
    private LocalTime heurEntrer;
    private LocalTime heureSorti;
    private String createBy;
}
