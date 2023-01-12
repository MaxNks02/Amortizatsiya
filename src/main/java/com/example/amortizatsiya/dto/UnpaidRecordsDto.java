package com.example.amortizatsiya.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnpaidRecordsDto {

    @JsonProperty("record")
    Long record;

    @JsonProperty("payment_date")
    String paymentDate;

    @JsonProperty("account")
    String account;

    @JsonProperty("income_outcome_account")
    String incomeOutcomeAccount;

    @JsonProperty("total_sum")
    String totalSum;

    @JsonProperty("payment_purpose")
    String paymentPurpose;

    @JsonProperty("code")
    String code;
}
