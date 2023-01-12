package com.example.amortizatsiya.controller;

import com.example.amortizatsiya.dto.UserDto;
import com.example.amortizatsiya.dto.auth.LoginRequest;
import com.example.amortizatsiya.dto.auth.LoginResponse;
import com.example.amortizatsiya.dto.auth.RegisterDto;
import com.example.amortizatsiya.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;



@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final AuthService service;

//    @PostMapping(value = "register")
//    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto dto) {
//        return ResponseEntity.ok(service.register(dto));
//    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}
