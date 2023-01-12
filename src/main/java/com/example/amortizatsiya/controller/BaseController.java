package com.example.amortizatsiya.controller;

import com.example.amortizatsiya.service.BaseService;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public abstract class BaseController<S extends BaseService> {
    protected S service;

    public BaseController(S service) {
        this.service = service;
    }
}
