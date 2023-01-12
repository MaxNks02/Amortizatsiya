package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepo<E extends BaseEntity> extends JpaRepository<E, Long> {
}
