package com.example.amortizatsiya;

import com.example.amortizatsiya.service.AmortizationIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmortizatsiyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmortizatsiyaApplication.class, args);
    }
}
 