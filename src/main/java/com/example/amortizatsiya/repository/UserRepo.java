package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.security.entity.Users;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends BaseRepo<Users> {
    Users findByUsername(String username);

    Boolean existsByUsername(String username);
}
