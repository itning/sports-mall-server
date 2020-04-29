package com.sport.sportsmallserver.exception;

import org.springframework.http.HttpStatus;

/**
 * 主键不存在
 *
 * @author itning
 * @date 2020/2/12 11:34
 */
public class IdNotFoundException extends BaseException {
    public IdNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
