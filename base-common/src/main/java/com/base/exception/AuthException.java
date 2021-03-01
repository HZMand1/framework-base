package com.base.exception;

/**
 * 权限异常
 *
 * @author sqa
 */
public class AuthException extends RuntimeException  {

    public AuthException(String message) {
        super(message);
    }
}
