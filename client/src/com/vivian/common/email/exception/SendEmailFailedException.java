package com.vivian.common.email.exception;

/**
 * @Author zhangjian
 * @Date 2015/10/15
 */
public class SendEmailFailedException extends RuntimeException {
    public SendEmailFailedException(String message) {
        super(message);
    }

    public SendEmailFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
