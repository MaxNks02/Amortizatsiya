package com.example.amortizatsiya.mapper;

import com.example.amortizatsiya.dto.AmortizationMfoDto;
import com.example.amortizatsiya.model.MfoDetails;
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
public interface MfoDetailsMapper extends BaseMapper<MfoDetails, AmortizationMfoDto>{
}
