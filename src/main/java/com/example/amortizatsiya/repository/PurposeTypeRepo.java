package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.lov.PurposeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurposeTypeRepo extends JpaRepository<PurposeType, Long> {
    Optional<PurposeType> findByName(String name);

    Optional<PurposeType> findByCode(String code);

    Optional<PurposeType> findByNameOrCode(String name, String code);
}
