package com.example.amortizatsiya.mapper;

import com.example.amortizatsiya.dto.AmortizationIncomeDto;
import com.example.amortizatsiya.model.AmortizationIncome;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
@Component
public interface AmortizationMapper extends BaseMapper<AmortizationIncome, AmortizationIncomeDto>{
}
