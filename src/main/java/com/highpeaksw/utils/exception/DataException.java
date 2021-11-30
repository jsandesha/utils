package com.highpeaksw.utils.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataException extends Exception {

    private final String errorCode;

    private final String errorMessage;

    private final HttpStatus httpStatus;

    public DataException( String errorCode, String errorMessage, HttpStatus httpStatus )
    {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
