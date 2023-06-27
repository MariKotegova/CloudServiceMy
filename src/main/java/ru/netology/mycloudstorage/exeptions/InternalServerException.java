package ru.netology.mycloudstorage.exeptions;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class InternalServerException extends InternalAuthenticationServiceException {
    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(String message) {
        super(message);
    }
}
