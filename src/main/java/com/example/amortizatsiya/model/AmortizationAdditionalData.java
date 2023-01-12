package com.example.amortizatsiya.model;

import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "amortization_income_additional_data")
public class AmortizationAdditionalData extends BaseEntity{
    @Column(name = "account")
    String account;

    @Column(name = "start_date")
    String startDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Column(name = "end_date")
    String endDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Column(name = "total_sum")
    String TotalSum;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "service_type", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    ServiceType serviceType;

    @Column(name = "name")
    String name;

    @Column(name = "account_income_outcome")
    String accountIncomeOutcome;

    @Column(name = "mfo")
    String mfo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "purpose_type", referencedColumnName = "id", nullable = false)
    PurposeType purposeCode;



}
