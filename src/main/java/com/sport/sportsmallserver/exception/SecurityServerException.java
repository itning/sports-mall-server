package com.sport.sportsmallserver.exception;

import org.springframework.http.HttpStatus;

/**
 * @author itning
 * @date 2020/2/12 11:18
 */
public class SecurityServerException extends BaseException {
    public SecurityServerException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
