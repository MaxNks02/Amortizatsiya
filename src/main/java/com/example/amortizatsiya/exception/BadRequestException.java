package com.example.amortizatsiya.exception;

import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ApiErrorMessages.BAD_REQUEST)
public class BadRequestException extends BaseException {

    public BadRequestException() {
        this(ApiErrorMessages.BAD_REQUEST);
    }

    public BadRequestException(String s) {
        super(s);
    }
}
