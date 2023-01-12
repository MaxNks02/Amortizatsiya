package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.lov.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long> {
    Optional<ServiceType> findByName(String name);

    Optional<ServiceType> findByCode(String code);

    Optional<ServiceType> findByNameOrCode(String name, String code);

}
