package com.example.amortizatsiya.dto;

import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmortizationSaveDto extends BaseDto {

    @NotEmpty(message = "account cannot be empty or null!")
    @JsonProperty("account")
    String account;

    @JsonProperty("remainder")
    String remainder;

    @NotEmpty(message = "created date cannot be empty")
    @JsonProperty("start_date")
    String createdDate;

    @NotEmpty(message = "end date cannot be empty")
    @JsonProperty("end_date")
    String endDate;

    @NotEmpty(message = "Total sum cannot be empty or null!")
    @JsonProperty("total_sum")
    String totalSum;

    @JsonProperty("amortization_sum_one_day")
    String amortizationSumOneDay;

    @JsonProperty("paid_sum")
    String paidSum;

    @NotEmpty(message = "service type cannot be empty or null!")
    @JsonProperty("service_type")
    ServiceTypeDto serviceType;

    @NotEmpty(message = "name cannot be empty or null!")
    @JsonProperty("name")
    String name;

    @NotEmpty(message = "income outcome account cannot be empty or null!")
    @JsonProperty("account_income_outcome")
    String accountIncomeOutcome;

//    @NotEmpty(message = "purpose type cannot be empty or null!")
//    @JsonProperty("purpose_type")
//    PurposeType purposeType;

    @NotEmpty(message = "mfo cannot be empty or null!")
    @JsonProperty("mfo")
    String mfo;
}
