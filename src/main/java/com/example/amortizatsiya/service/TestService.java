package com.example.amortizatsiya.service;

import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.model.AmortizationAdditionalData;
import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.example.amortizatsiya.repository.AmortizationAdditionalDataRepo;
import com.example.amortizatsiya.repository.PurposeTypeRepo;
import com.example.amortizatsiya.repository.ServiceTypeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {
    private final AmortizationAdditionalDataRepo amortizationAdditionalDataRepo;
    private final ServiceTypeRepo serviceTypeRepo;
    private final PurposeTypeRepo purposeTypeRepo;

    public TestService(AmortizationAdditionalDataRepo amortizationAdditionalDataRepo, ServiceTypeRepo serviceTypeRepo, PurposeTypeRepo purposeTypeRepo) {
        this.amortizationAdditionalDataRepo = amortizationAdditionalDataRepo;
        this.serviceTypeRepo = serviceTypeRepo;
        this.purposeTypeRepo = purposeTypeRepo;
    }

    public ResponseEntity<?> get(String mfo, String paymentPurpose, String paymentPurposeCode, String serviceType, String serviceTypeCode){
        Optional<ServiceType> serviceType1 = serviceTypeRepo.findByName(serviceType);
        Optional<PurposeType> purposeTypeOptional = purposeTypeRepo.findByName(paymentPurpose);
        if (serviceType1.isEmpty()){
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service type not found!"));
        }

        if (purposeTypeOptional.isEmpty() || purposeTypeRepo.findByCode(paymentPurposeCode).isEmpty()){
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "purpose type not found!"));
        }

        if (!serviceType1.get().getCode().equals(serviceTypeCode)){
            throw new CustomNotFoundException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type code has not matched"));
        }
        Optional<AmortizationAdditionalData> amortizationAdditionalData =
                amortizationAdditionalDataRepo.findByPurposeCodeAndServiceTypeAndMfo(purposeTypeOptional.get(), serviceType1.get(), mfo);
        if (amortizationAdditionalData.isEmpty()){
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "data not found !. Check parameters"));
        }

        return ResponseHandler.generateResponse("transaction data : " , HttpStatus.OK, amortizationAdditionalData.get());
    }
}
