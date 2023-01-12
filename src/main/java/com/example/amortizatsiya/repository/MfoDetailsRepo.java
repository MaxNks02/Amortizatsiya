package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.MfoDetails;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MfoDetailsRepo extends BaseRepo<MfoDetails> {
    Optional<MfoDetails> findByMfo(String mfo);

    Optional<MfoDetails> findByIncomeAccount(String account);
}
