package com.example.amortizatsiya.model;

import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mfo_details")
public class MfoDetails extends BaseEntity {

    @Column(name = "mfo")
    String mfo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purpose_type")
    PurposeType purposeType;


    @Column(name = "income_account")
    String incomeAccount;

    @OneToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "service_type")
    ServiceType serviceType;

}
