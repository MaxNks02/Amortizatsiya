package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.lov.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepo extends JpaRepository<Records, Long> {
}
