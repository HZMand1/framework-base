package com.base.exception;

/**
 * YunLu系统异常
 *
 * @author sqa
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -6916154462432027437L;

    public BaseException(String message) {
        super(message);
    }
}
