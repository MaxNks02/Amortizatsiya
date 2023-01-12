package com.example.amortizatsiya.model.lov;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "records")
public class Records extends LOVEntity {



    @Column(name = "payment_date")
    String paymentDate;
    @Column(name = "account")
    String account;
    @Column(name = "income_outcome_account")
    String incomeOutcomeAccount;
    @Column(name = "total_sum")
    String totalSum;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_purpose_id", referencedColumnName = "id", nullable = false)
    PurposeType paymentPurpose;

    @Column(name = "code")
    String code;
}
