package com.example.amortizatsiya.controller;

import com.example.amortizatsiya.dto.AmortizationIncomeDto;
import com.example.amortizatsiya.dto.AmortizationSaveDto;
import com.example.amortizatsiya.service.AmortizationIncomeService;
import com.example.amortizatsiya.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/amortization_income")
public class AmortizationIncomeController extends BaseController<AmortizationIncomeService> {
    private final Logger logger = LoggerFactory.getLogger(AmortizationIncomeController.class);

    private final TestService testService;

    public AmortizationIncomeController(AmortizationIncomeService service, TestService testService) {
        super(service);
        this.testService = testService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return service.getAllAmortizationIncomes();
    }

    @GetMapping("/getAllByState")
    public ResponseEntity<?> getAllByState(@RequestParam(value = "type", required = false) String type) {
        logger.info("Request to get all data. Time: " + LocalDateTime.now());
        return service.getAllByState(type);
    }
    @GetMapping("/getByAccount")
    public ResponseEntity<?> getByAccount(@RequestParam(name = "account") String account){
        return service.getByAccount(account);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getByID(id);
    }

    @GetMapping("/getEnteredAndValidated")
    public ResponseEntity<?> getEnteredAndValidated() {
        return service.getEnteredAndValidated();
    }

    @GetMapping("/getByType")
    public ResponseEntity<?> getByType(@RequestParam String type){
        return  service.getByType(type);
    }

    @GetMapping("/getHistory/{id}")
    public ResponseEntity<?> getHistory(@PathVariable Long id){
        return service.getHistoryTransactions(id);
    }

//    @GetMapping("/get")
//    public ResponseEntity<?> get(
//            @RequestParam(name = "mfo") String MFO, @RequestParam(name = "payment_purpose") String paymentPurpose,
//            @RequestParam(name = "payment_code") String paymentCode, @RequestParam(name = "service_type") String serviceType,
//            @RequestParam(name = "service_type_code") String serviceTypeCode) {
//        return testService.get(MFO, paymentPurpose, paymentCode, serviceType, serviceTypeCode);
//    }

//    @GetMapping("/get")
//    public ResponseEntity<?> get(){
//        return service
//    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AmortizationSaveDto amortizationIncomeDto){
        return service.save(
                amortizationIncomeDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }

@GetMapping("/getRecords")
public ResponseEntity<?> getRecords(){
        return service.getRecords();
}

    @PostMapping("/DoPayment")
    public ResponseEntity<?> doPayment(){
       return service.doPayment();
    }
}
