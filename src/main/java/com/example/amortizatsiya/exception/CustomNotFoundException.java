package com.example.amortizatsiya.exception;

import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ApiErrorMessages.NOT_FOUND)
public class CustomNotFoundException extends BaseException {

    public CustomNotFoundException() {
        this(ApiErrorMessages.NOT_FOUND);
    }

    public CustomNotFoundException(String s) {
        super(s);
    }

}
