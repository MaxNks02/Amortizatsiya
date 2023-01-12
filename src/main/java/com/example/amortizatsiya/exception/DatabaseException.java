package com.example.amortizatsiya.exception;

import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = ApiErrorMessages.INTERNAL_SERVER_ERROR)
public class DatabaseException extends BaseException {

    public DatabaseException() {
        this(ApiErrorMessages.INTERNAL_SERVER_ERROR);
    }

    public DatabaseException(String s) {
        super(s);
    }
}
