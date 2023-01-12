package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.PurposeTypeDto;
import com.example.amortizatsiya.exception.BadRequestException;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.repository.PurposeTypeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PurposeTypeService {
    private final PurposeTypeRepo purposeTypeRepo;

    public PurposeTypeService(PurposeTypeRepo purposeTypeRepo) {
        this.purposeTypeRepo = purposeTypeRepo;
    }


    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse("Service List : ", HttpStatus.OK, purposeTypeRepo.findAll());
    }

    public ResponseEntity<?> getByCode(String code) {
        Optional<PurposeType> getByCodeOptional = purposeTypeRepo.findByCode(code);
        if (getByCodeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service code not found!"));
        }
        return ResponseHandler.generateResponse("service by code : ", HttpStatus.FOUND, getByCodeOptional.get());
    }

    public ResponseEntity<?> getByName(String name) {
        Optional<PurposeType> getByNameOptional = purposeTypeRepo.findByName(name);
        if (getByNameOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service code not found!"));
        }
        return ResponseHandler.generateResponse("service by code : ", HttpStatus.FOUND, getByNameOptional.get());
    }

    public ResponseEntity<?> save(PurposeTypeDto dto) {
        checkService(dto.getName(), dto.getCode());

        if (Objects.isNull(dto.getCode())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Code cannot be null. Please check parameter!"));
        }



        if (Objects.isNull(dto.getName())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Name cannot be null. Please check parameter!"));
        }

        PurposeType purposeType = new PurposeType();
        purposeType.setName(dto.getName());
        purposeType.setCode(dto.getCode());

        purposeTypeRepo.save(purposeType);

        return ResponseHandler.generateResponse("Saved", HttpStatus.OK);
    }

    public ResponseEntity<?> edit(Long id, PurposeTypeDto dto) {


        if (Objects.isNull(dto.getCode())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Code cannot be null. Please check parameter!"));
        }


        if (Objects.isNull(dto.getName())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Name cannot be null. Please check parameter!"));
        }

        checkService(dto.getName(), dto.getCode());

        Optional<PurposeType> purposeTypeOptional = purposeTypeRepo.findById(id);
        PurposeType purposeType = purposeTypeOptional.get();
        purposeType.setName(dto.getName());
        purposeType.setCode(dto.getCode());

        purposeTypeRepo.save(purposeType);

        return ResponseHandler.generateResponse("Edited ", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<PurposeType> purposeTypeOptional = purposeTypeRepo.findById(id);
        if (purposeTypeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "purposeType not found"));
        }
        purposeTypeRepo.delete(purposeTypeOptional.get());
        return ResponseHandler.generateResponse("Deleted", HttpStatus.OK);


    }


    private void checkService(String name, String code) {
        Optional<PurposeType> getByCodeOptional = purposeTypeRepo.findByCode(code);
        if (!getByCodeOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Code already exist. Please check parameter!"));
        }

        Optional<PurposeType> getByNameOptional = purposeTypeRepo.findByName(name);
        if (!getByNameOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Name already exist. Please check parameter!"));
        }
    }



}
