package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // 👇 Si w ap mete li nan HashMap, ou bezwen sa pou .equals/.hashCode mache
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodeDTO)) return false;
        PeriodeDTO that = (PeriodeDTO) o;
        return dateDebut.equals(that.dateDebut) && dateFin.equals(that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDebut, dateFin);
    }
}
