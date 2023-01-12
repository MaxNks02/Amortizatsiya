package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.AmortizationIncomeDto;
import com.example.amortizatsiya.dto.AmortizationSaveDto;
import com.example.amortizatsiya.exception.BadRequestException;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.DatabaseException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.mapper.AmortizationMapper;
import com.example.amortizatsiya.model.AmortizationIncome;
import com.example.amortizatsiya.model.MfoDetails;
import com.example.amortizatsiya.model.lov.*;
import com.example.amortizatsiya.repository.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AmortizationIncomeService extends BaseService<AmortizationIncomeRepo, AmortizationIncome, AmortizationIncomeDto, AmortizationMapper> {


    private final ServiceTypeRepo serviceTypeRepo;
    private final RecordsRepo recordsRepo;

    private final PurposeTypeRepo purposeTypeRepo;
    private final HistoryTransactionsRepo historyTransactionsRepo;

    private final MfoDetailsRepo mfoDetailsRepo;


    public AmortizationIncomeService(AmortizationIncomeRepo amortizationIncomeRepo, @Qualifier("amortizationMapperImpl") AmortizationMapper mapper, ServiceTypeRepo serviceTypeRepo, RecordsRepo recordsRepo, PurposeTypeRepo purposeTypeRepo, HistoryTransactionsRepo historyTransactionsRepo, MfoDetailsRepo mfoDetailsRepo) {
        super(amortizationIncomeRepo, mapper);
        this.serviceTypeRepo = serviceTypeRepo;
        this.recordsRepo = recordsRepo;
        this.purposeTypeRepo = purposeTypeRepo;
        this.historyTransactionsRepo = historyTransactionsRepo;
        this.mfoDetailsRepo = mfoDetailsRepo;
    }

    public ResponseEntity<?> getAllAmortizationIncomes() {
        return ResponseHandler.generateResponse("all amortization List", HttpStatus.OK, getRepository().findAll());
    }

    public ResponseEntity<?> getAllByState(String type) {
        List<AmortizationIncome> amortizationIncomeListList;
        List<AmortizationIncomeDto> amortizationIncomeDtoList;
        if (!Strings.isNullOrEmpty(type)) {
            switch (type) {
                case "deleted" -> {
                    amortizationIncomeListList = getRepository().findAllByState(State.DELETED);
                    amortizationIncomeDtoList = entityListToDtoList(amortizationIncomeListList);
                }
                case "validated" -> {
                    amortizationIncomeListList = getRepository().findAllByState(State.VALIDATED);
                    amortizationIncomeDtoList = entityListToDtoList(amortizationIncomeListList);
                }
                case "entered" -> {
                    amortizationIncomeListList = getRepository().findAllByState(State.ENTERED);
                    amortizationIncomeDtoList = entityListToDtoList(amortizationIncomeListList);
                }
                default ->
                        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Please check parameter!"));
            }
            return ResponseHandler.generateResponse("All " + type + " amortization transactions !", HttpStatus.OK, amortizationIncomeDtoList);
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Please check parameter!"));

    }

//    public ResponseEntity<?> get() {
//        AmortizationMfoDto amortizationMfoDto = new AmortizationMfoDto();
//        List<AmortizationMfoDto> amortizationMfoDtoList = new ArrayList<>();
//        List<AmortizationIncome> amortizationIncomeList = getRepository().findAll();
//        for (AmortizationIncome amortizationIncome : amortizationIncomeList) {
//            amortizationMfoDto.setMfo(amortizationIncome.getMfo());
//            amortizationMfoDto.setPurposeTypeCode(amortizationIncome.getRecord().getPaymentPurpose().getCode());
//            amortizationMfoDto.setPurposeTypeName(amortizationIncome.getRecord().getPaymentPurpose().getCode());
//            amortizationMfoDto.setIncomeAccount(amortizationIncome.getAccountIncomeOutcome());
//            amortizationMfoDto.setServiceTypeName(amortizationIncome.getServiceType().getName());
//            amortizationMfoDto.setServiceTypeCode(amortizationIncome.getServiceType().getCode());
//            amortizationMfoDtoList.add(amortizationMfoDto);
//        }
//        return ResponseHandler.generateResponse("Amortization base List : ")
//
//    }

    public ResponseEntity<?> getByAccount(String account) {
        Optional<AmortizationIncome> amortizationIncomeOptional = getRepository().findByAccount(account);
        if (amortizationIncomeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Amortization details not found!"));
        }
        AmortizationIncomeDto amortizationIncomeDto = getMapper().convertFromEntity(amortizationIncomeOptional.get());
        return ResponseHandler.generateResponse("Amortization details : ", HttpStatus.FOUND, amortizationIncomeDto);
    }

    public ResponseEntity<?> getEnteredAndValidated() {
        List<AmortizationIncome> amortizationIncomeListList = getRepository().findAll().
                stream().
                filter(entity -> entity.getState().equals(State.VALIDATED) || entity.getState().equals(State.ENTERED)).
                toList();
        List<AmortizationIncomeDto> amortizationIncomeDtoList = entityListToDtoList(amortizationIncomeListList);

        return ResponseHandler.generateResponse("Entered and Validated transactions : ", HttpStatus.OK, amortizationIncomeDtoList);
    }

    public ResponseEntity<?> getByID(Long id) {
        Optional<AmortizationIncome> amortizationIncomeOptional = getRepository().findById(id);

        if (amortizationIncomeOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service type not found!"));
        }

        return ResponseHandler.generateResponse("amortization income transaction : ", HttpStatus.OK, amortizationIncomeOptional.get());
    }

    public ResponseEntity<?> getByType(String serviceType) {
        List<AmortizationIncome> amortizationIncomeListList = getRepository().findAll().
                stream().
                filter(e -> e.getServiceType().getName().equals(serviceType)).
                toList();
        List<AmortizationIncomeDto> amortizationIncomeDtoList = entityListToDtoList(amortizationIncomeListList);

        return ResponseHandler.generateResponse("type " + serviceType + " transactions : ", HttpStatus.OK, amortizationIncomeListList);
    }


    public ResponseEntity<?> getHistoryTransactions(Long amortizationId) {
        List<HistoryTransactions> historyTransactionsList = historyTransactionsRepo.findAll().
                stream().
                filter(n -> Objects.equals(n.getAmortizationIncome().getId(), amortizationId)).
                toList();
        return ResponseHandler.generateResponse("History of Transaction", HttpStatus.OK, historyTransactionsList);

    }

    public ResponseEntity<?> getRecords() {
//        List<AmortizationIncome> amortizationIncomeList = getRepository().findAllByState(State.ENTERED);
//        List<UnpaidRecordsDto> unpaidRecordsDtoList = new ArrayList<>();
        List<Records> recordsList = recordsRepo.findAll().stream().filter(n -> Double.parseDouble(n.getTotalSum()) > 0).collect(Collectors.toList());

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate now = LocalDate.parse(LocalDate.now().toString(), formatter);
//        for (AmortizationIncome amortizationincome : amortizationIncomeList) {
//            Long sum;
//
//            LocalDate paymentDate;
//            if (amortizationincome.getPaidDAte() == null) {
//                paymentDate = LocalDate.parse(amortizationincome.getStartDate(), formatter);
//            } else {
//                paymentDate = LocalDate.parse(amortizationincome.getPaidDAte(), formatter);
//            }
//            sum = Integer.parseInt(amortizationincome.getSumAmortizationOneDay()) * (ChronoUnit.DAYS.between(now, paymentDate));
//            UnpaidRecordsDto unpaidRecordsDto = new UnpaidRecordsDto();
//            unpaidRecordsDto.setRecord(amortizationincome.getRecord().getId());
//            unpaidRecordsDto.setPaymentDate(now.toString());
//            unpaidRecordsDto.setAccount(amortizationincome.getAccount());
//            unpaidRecordsDto.setIncomeOutcomeAccount(amortizationincome.getAccountIncomeOutcome());
//            unpaidRecordsDto.setTotalSum(String.valueOf(sum));
//
////           Optional<ServiceType> serviceType = serviceTypeRepo.findById(amortizationIncome.getServiceType().getId());
//            unpaidRecordsDto.setCode(amortizationincome.getServiceType().getCode());
//            unpaidRecordsDto.setPaymentPurpose(amortizationincome.getServiceType().getName());
//            unpaidRecordsDtoList.add(unpaidRecordsDto);
//
//        }
        return ResponseHandler.generateResponse("unpaid transactions list : ", HttpStatus.OK, recordsList);

    }


    @Transactional
    public ResponseEntity<?> doPayment() {
        List<Records> recordsList = recordsRepo.findAll();
        for (Records records : recordsList) {
            Optional<AmortizationIncome> amortizationIncomeOptional = getRepository().findByRecordId(records.getId());
            if (amortizationIncomeOptional.isEmpty()) {
                throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service type not found!"));
            }
            if (Long.parseLong(amortizationIncomeOptional.get().getRemainder()) < Long.parseLong(records.getTotalSum())) {
                throw new CustomNotFoundException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Records total sum cannot be greater than amortization remainder ! (record_id : " + records.getId() + " )"));
            }
            AmortizationIncome amortizationIncome = amortizationIncomeOptional.get();
            amortizationIncome.setPaidDAte(records.getPaymentDate());
//            amortizationIncome.setRemainder(String.valueOf(Long.parseLong(amortizationIncome.getRemainder())-Long.parseLong(unpaidRecordsDto.getTotalSum())));

            amortizationIncome.setRemainder(String.valueOf(Long.parseLong(amortizationIncome.getRemainder()) - Long.parseLong(records.getTotalSum())));
            records.setTotalSum("0");

            if (amortizationIncome.getRemainder().equals("0")) {
                amortizationIncome.setState(State.VALIDATED);
            }
            HistoryTransactions historyTransactions = new HistoryTransactions();
            historyTransactions.setState(State.ENTERED);
            historyTransactions.setAmortizationIncome(amortizationIncome);

            historyTransactionsRepo.save(historyTransactions);
            getRepository().save(amortizationIncome);
            recordsRepo.save(records);
        }

        return ResponseHandler.generateResponse("Payment done !", HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> save(AmortizationSaveDto amortizationIncomeDto) {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate localDate = LocalDate.now();
        AmortizationIncome amortizationIncome = new AmortizationIncome();
        amortizationIncome.setAccount(amortizationIncomeDto.getAccount());
        amortizationIncome.setEndDate(amortizationIncomeDto.getEndDate());
        amortizationIncome.setStartDate(amortizationIncomeDto.getCreatedDate());
        amortizationIncome.setRemainder(amortizationIncomeDto.getTotalSum());
        amortizationIncome.setTotalSum(amortizationIncomeDto.getTotalSum());
        amortizationIncome.setName(amortizationIncomeDto.getName());
        amortizationIncome.setAccountIncomeOutcome(amortizationIncomeDto.getAccountIncomeOutcome());
        amortizationIncome.setSumAmortizationOneDay(amortizationIncomeDto.getAmortizationSumOneDay());
        amortizationIncome.setState(State.ENTERED);
        amortizationIncome.setMfo(amortizationIncomeDto.getMfo());
        Optional<ServiceType> serviceType = serviceTypeRepo.findByCode(amortizationIncomeDto.getServiceType().getCode());
        if (serviceType.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "service type not found!"));
        }
        amortizationIncome.setServiceType(serviceType.get());

        Optional<MfoDetails> mfoDetailsOptional = mfoDetailsRepo.findByIncomeAccount(amortizationIncomeDto.getAccountIncomeOutcome());

//        Optional<PurposeType> purposeTypeOptional = purposeTypeRepo.findByName(amortizationIncomeDto.getPurposeType().getName());
        if (mfoDetailsOptional.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "income account not found!"));
        }


        Records records = new Records();
        records.setAccount(amortizationIncomeDto.getAccount());
        records.setCode(serviceType.get().getCode());
        records.setPaymentDate(amortizationIncome.getStartDate());
        records.setIncomeOutcomeAccount(amortizationIncomeDto.getAccountIncomeOutcome());
        records.setTotalSum(amortizationIncomeDto.getAmortizationSumOneDay());
        records.setPaymentPurpose(mfoDetailsOptional.get().getPurposeType());
        records.setCode(serviceType.get().getCode());
        amortizationIncome.setRecord(records);
        recordsRepo.save(records);
        AmortizationIncome amortizationIncome1 = getRepository().save(amortizationIncome);


        return ResponseHandler.generateResponse("saved", HttpStatus.OK, amortizationIncome1);

    }


    public ResponseEntity<?> delete(Long id) {
        AmortizationIncome entity = getRepository().findById(id).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "transaction not found")));
        if (entity.getState().equals(State.VALIDATED)) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Data whose state is validated cannot be deleted"));
        }
        entity.setState(State.DELETED);
        try {
            getRepository().save(entity);
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + "%s", exception.getMessage()));
        }

        return ResponseHandler.generateResponse("Successfully deleted rows!", HttpStatus.OK);

    }

    @Scheduled(cron = "0 0 18 * * *")
    protected void everyDayAccountRecords() {
        System.out.println("cron worked");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.now();
        String now = formatter.format(localDate);
        List<Records> recordsList = recordsRepo.findAll();
        for (Records records : recordsList) {
            Optional<AmortizationIncome> amortizationIncomeOptional = getRepository().findByRecordId(records.getId());
            if (
                    Double.parseDouble(amortizationIncomeOptional.get().getRemainder()) > 0
                            && ChronoUnit.DAYS.between(localDate, LocalDate.parse(amortizationIncomeOptional.get().getEndDate(), formatter)) >= 0
                            && Double.parseDouble(records.getTotalSum()) <= Long.parseLong(amortizationIncomeOptional.get().getRemainder())
                            && ChronoUnit.DAYS.between(localDate, LocalDate.parse(amortizationIncomeOptional.get().getStartDate(), formatter)) < 0
            ) {
                Double sum = Double.parseDouble(amortizationIncomeOptional.get().getSumAmortizationOneDay()) + Double.parseDouble(records.getTotalSum());
                records.setPaymentDate(now);
                records.setTotalSum(String.valueOf(sum));
                recordsRepo.save(records);
            }
        }
    }


    @Override
    public AmortizationIncomeDto update(AmortizationIncomeDto dto, long id) {
        return null;
    }


}
