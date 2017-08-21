package com.future.exception;

/**
 * Created by Administrator on 2017/1/30.
 */
public class IllegalLoginException extends RuntimeException {
    public IllegalLoginException(String message) {
        super(message);
    }
}
