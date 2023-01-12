package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.AmortizationIncome;
import com.example.amortizatsiya.model.lov.Records;
import com.example.amortizatsiya.model.lov.State;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmortizationIncomeRepo extends BaseRepo<AmortizationIncome> {
    List<AmortizationIncome> findAllByState(State state);

    Optional<AmortizationIncome> findByRecordId(Long record);

    Optional<AmortizationIncome> findByAccount(String account);

}
