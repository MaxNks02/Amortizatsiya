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
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmortizationMfoDto extends BaseDto {

    @NotEmpty(message = "mfo cannot be empty or null!")
    @JsonProperty("mfo")
    String mfo;

    @NotEmpty(message = "purpose_type cannot be empty or null!")
    @JsonProperty("purpose_type")
    PurposeType purposeType;

    @NotEmpty(message = "income_account cannot be empty or null!")
    @JsonProperty("income_account")
    String incomeAccount;

    @NotEmpty(message = "service_type cannot be empty or null!")
    @JsonProperty("service_type")
    ServiceType serviceType;

}
