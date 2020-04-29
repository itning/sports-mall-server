package com.sport.sportsmallserver.exception;

import org.springframework.http.HttpStatus;

/**
 * 传入的参数为空
 *
 * @author itning
 * @date 2020/2/12 11:34
 */
public class NullFiledException extends BaseException {
    public NullFiledException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
