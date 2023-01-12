package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.AmortizationAdditionalData;
import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmortizationAdditionalDataRepo extends JpaRepository<AmortizationAdditionalData, Long> {
    Optional<AmortizationAdditionalData>  findByPurposeCodeAndServiceTypeAndMfo(PurposeType purposeType, ServiceType serviceType, String mfo);
}
