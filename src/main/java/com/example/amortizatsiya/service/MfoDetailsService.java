package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.AmortizationMfoDto;
import com.example.amortizatsiya.exception.BadRequestException;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.mapper.MfoDetailsMapper;
import com.example.amortizatsiya.model.MfoDetails;
import com.example.amortizatsiya.model.lov.LOVEntity;
import com.example.amortizatsiya.model.lov.PurposeType;
import com.example.amortizatsiya.model.lov.ServiceType;
import com.example.amortizatsiya.repository.MfoDetailsRepo;
import com.example.amortizatsiya.repository.PurposeTypeRepo;
import com.example.amortizatsiya.repository.ServiceTypeRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MfoDetailsService extends BaseService<MfoDetailsRepo, MfoDetails, AmortizationMfoDto, MfoDetailsMapper> {

    private final ServiceTypeRepo serviceTypeRepo;

    private final PurposeTypeRepo purposeTypeRepo;

    public MfoDetailsService(MfoDetailsRepo mfoDetailsRepo, @Qualifier("mfoDetailsMapperImpl") MfoDetailsMapper mapper, ServiceTypeRepo serviceTypeRepo, PurposeTypeRepo purposeTypeRepo) {
        super(mfoDetailsRepo, mapper);
        this.serviceTypeRepo = serviceTypeRepo;
        this.purposeTypeRepo = purposeTypeRepo;
    }


    public ResponseEntity<?> get() {
        List<MfoDetails> mfoDetailsList = getRepository().findAll();
        List<AmortizationMfoDto> amortizationMfoDtoList = getMapper().convertFromEntityList(mfoDetailsList);
        return ResponseHandler.generateResponse("mfo details List : ", HttpStatus.OK, amortizationMfoDtoList);
    }

    public ResponseEntity<?> editCode(String code, Long id) {
        Optional<MfoDetails> mfoDetailsOptional = getRepository().findById(id);
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepo.findByCode(code);
        if (mfoDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "data not found !. Check parameters"));
        }
        if (!serviceTypeOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Code already exist. Please check parameter!"));
        }
        MfoDetails mfoDetails = mfoDetailsOptional.get();

        mfoDetails.getServiceType().setCode(code);

        getRepository().save(mfoDetails);

        return ResponseHandler.generateResponse("updated", HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<MfoDetails> mfoDetailsOptional = getRepository().findById(id);
        if (mfoDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "data not found !. Check parameters"));
        }
        return ResponseHandler.generateResponse("mfo detail : ", HttpStatus.FOUND, mfoDetailsOptional.get());
    }

    public ResponseEntity<?> save(AmortizationMfoDto dto) {
        Optional<MfoDetails> mfoDetailsOptional = getRepository().findByMfo(dto.getMfo());
        if (!mfoDetailsOptional.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "mfo already exist. Please check parameter!"));
        }
        List<LOVEntity> lovEntities = checkServiceTypeAndPurposeType(dto.getServiceType().getName(), dto.getServiceType().getCode(),
                dto.getPurposeType().getName(), dto.getPurposeType().getCode());
        MfoDetails mfoDetails = new MfoDetails();
        mfoDetails.setMfo(dto.getMfo());
        mfoDetails.setServiceType((ServiceType) lovEntities.get(0));
        mfoDetails.setIncomeAccount(dto.getIncomeAccount());
        mfoDetails.setPurposeType((PurposeType) lovEntities.get(1));

        getRepository().save(mfoDetails);

        return ResponseHandler.generateResponse("saved", HttpStatus.OK);
    }

    public ResponseEntity<?> edit(AmortizationMfoDto dto, Long id) {
        Optional<MfoDetails> mfoDetailsOptional = getRepository().findById(id);
        if (mfoDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "data not found !. Check parameters"));
        }

        if (!getRepository().findByMfo(dto.getMfo()).isEmpty() && !mfoDetailsOptional.get().getMfo().equals(dto.getMfo())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "mfo already exist. Please check parameter!"));
        }

        List<LOVEntity> lovEntities = checkServiceTypeAndPurposeType(dto.getServiceType().getName(), dto.getServiceType().getCode(),
                dto.getPurposeType().getName(), dto.getPurposeType().getCode());

        MfoDetails mfoDetails = mfoDetailsOptional.get();
        mfoDetails.setMfo(dto.getMfo());
        mfoDetails.setServiceType((ServiceType) lovEntities.get(0));
        mfoDetails.setIncomeAccount(dto.getIncomeAccount());
        mfoDetails.setPurposeType((PurposeType) lovEntities.get(1));

        getRepository().save(mfoDetails);

        return ResponseHandler.generateResponse("edited", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<MfoDetails> mfoDetailsOptional = getRepository().findById(id);
        if (mfoDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "data not found !. Check parameters"));
        }
        getRepository().delete(mfoDetailsOptional.get());

        return ResponseHandler.generateResponse("deleted", HttpStatus.OK);
    }

    private List<LOVEntity> checkServiceTypeAndPurposeType(String serviceTypeName, String serviceTypeCode, String purposeTypeName, String purposeTypeCode) {
//        Optional<PurposeType> purposeTypeOptional = purposeTypeRepo.findByName(purposeTypeName);
        checkPurpose(purposeTypeName, purposeTypeCode);
        PurposeType purposeType = new PurposeType();
        purposeType.setName(purposeTypeName);
        purposeType.setCode(purposeTypeCode);
        purposeTypeRepo.save(purposeType);

//        Optional<ServiceType> serviceTypeOptional = serviceTypeRepo.findByName(serviceTypeName);
        ServiceType serviceType = new ServiceType();
        checkService(serviceTypeName, serviceTypeCode);
        serviceType.setName(serviceTypeName);
        serviceType.setCode(serviceTypeCode);
        serviceTypeRepo.save(serviceType);


//        if(!serviceTypeOptional.isEmpty()){
//            serviceType = serviceTypeOptional.get();
//        }
//        if (!purposeTypeOptional.isEmpty()) {
//            purposeType = purposeTypeOptional.get();
//        }
        return List.of(serviceType, purposeType);
    }


    @Override
    public AmortizationMfoDto update(AmortizationMfoDto dto, long id) {
        return null;
    }

    private void checkService(String name, String code) {
        Optional<ServiceType> getByCodeOptional = serviceTypeRepo.findByCode(code);
        Optional<ServiceType> getByNameOptional = serviceTypeRepo.findByName(name);
        if ((!getByCodeOptional.isEmpty() || !getByNameOptional.isEmpty())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "service type code or name already exist. Please check parameter!"));
        }
    }

    private void checkPurpose(String name, String code) {
        Optional<PurposeType> getByCodeOptional = purposeTypeRepo.findByCode(code);
        Optional<PurposeType> getByNameOptional = purposeTypeRepo.findByName(name);
        if ((!getByCodeOptional.isEmpty() || !getByNameOptional.isEmpty())) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "purpose type code or name already exist. Please check parameter!"));
        }
    }
}

