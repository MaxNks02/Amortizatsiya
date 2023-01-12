package com.example.amortizatsiya.model;


import com.example.amortizatsiya.model.lov.Records;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.example.amortizatsiya.model.lov.State;
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
@Table(name = "amortization_income")
public class AmortizationIncome extends BaseEntity{

    @Column(name = "account")
    String account;

    @Column(name = "remainder")
    String remainder ;

    @Column(name = "start_date")
    String startDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Column(name = "end_date")
    String endDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Column(name = "total_sum")
    String totalSum;

    @Column(name = "sum_amortization_one_day")
    String sumAmortizationOneDay;


    @Column(name="paid_date")
    String paidDAte;

    @OneToOne
    @JoinColumn(name = "service_type", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    ServiceType serviceType;


    @Column(name = "name")
    String name;

    @Column(name = "account_income_outcome")
    String accountIncomeOutcome;

    @Column(name = "state")
    State state;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    Records record;

    @Column(name = "mfo")
    String mfo;




}
