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
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private BigDecimal balance; // Balans aktyèl prè a

    @Column(precision = 19, scale = 2)
    private BigDecimal accumulatedInterest = BigDecimal.ZERO; // Enterè total ki akimile

    @Column(nullable = false)
    private boolean interetCumule; // Kalite enterè (kopye depi nan kont lan)

    @Column(nullable = false)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    private boolean approved = false;

    private LocalDate dueDate;
    private String status;

}
