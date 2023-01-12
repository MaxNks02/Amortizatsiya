package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.security.entity.Roles;
import com.example.amortizatsiya.security.entity.enums.RoleName;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepo extends BaseRepo<Roles> {
    Roles findByName(RoleName user);
}
