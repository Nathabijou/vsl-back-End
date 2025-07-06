package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Loan {
    @Id
    private String idLoan;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Refund> refunds = new ArrayList<>();


    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;

}
