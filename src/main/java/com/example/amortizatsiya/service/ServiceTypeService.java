package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.ServiceTypeDto;
import com.example.amortizatsiya.exception.BadRequestException;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.example.amortizatsiya.repository.ServiceTypeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepo serviceTypeRepo;

    public ServiceTypeService(ServiceTypeRepo serviceTypeRepo) {
        this.serviceTypeRepo = serviceTypeRepo;
    }

    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse("Service List : ", HttpStatus.OK, serviceTypeRepo.findAll());
    }

    public ResponseEntity<?> getByCode(String code) {
        Optional<ServiceType> getByCodeOptional = serviceTypeRepo.findByCode(code);
        if (getByCodeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service code not found!"));
        }
        return ResponseHandler.generateResponse("service by code : ", HttpStatus.FOUND, getByCodeOptional.get());
    }

    public ResponseEntity<?> getByName(String name) {
        Optional<ServiceType> getByNameOptional = serviceTypeRepo.findByName(name);
        if (getByNameOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service type name not found!"));
        }
        return ResponseHandler.generateResponse("service by code : ", HttpStatus.FOUND, getByNameOptional.get());
    }

    public ResponseEntity<?> save(ServiceTypeDto dto) {
        checkService(dto.getName(), dto.getCode());

        if (Objects.isNull(dto.getCode())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type code cannot be null. Please check parameter!"));
        }


        if (Objects.isNull(dto.getName())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type name cannot be null. Please check parameter!"));
        }

        ServiceType serviceType = new ServiceType();
        serviceType.setName(dto.getName());
        serviceType.setCode(dto.getCode());

        serviceTypeRepo.save(serviceType);

        return ResponseHandler.generateResponse("Saved", HttpStatus.OK);
    }

    public ResponseEntity<?> edit(Long id, ServiceTypeDto dto) {


        if (Objects.isNull(dto.getCode())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Code cannot be null. Please check parameter!"));
        }


        if (Objects.isNull(dto.getName())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Name cannot be null. Please check parameter!"));
        }

        Optional<ServiceType> serviceTypeOptional = serviceTypeRepo.findById(id);


        checkService(dto.getName(), dto.getCode());

        ServiceType serviceType = serviceTypeOptional.get();
        serviceType.setName(dto.getName());
        serviceType.setCode(dto.getCode());

        serviceTypeRepo.save(serviceType);

        return ResponseHandler.generateResponse("Edited ", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepo.findById(id);
        if (serviceTypeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "serviceType not found"));
        }
        serviceTypeRepo.delete(serviceTypeOptional.get());
        return ResponseHandler.generateResponse("Deleted", HttpStatus.OK);


    }


    private void checkService(String name, String code) {
        Optional<ServiceType> getByCodeOptional = serviceTypeRepo.findByCode(code);
        if (!getByCodeOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type code already exist. Please check parameter!"));
        }

        Optional<ServiceType> getByNameOptional = serviceTypeRepo.findByName(name);
        if (!getByNameOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type name already exist. Please check parameter!"));
        }
    }


}
