package com.disquesea.disqueseaapi.domain.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException() {
        super("Business rule was violated");
    }

    public BusinessException(String message) {
        super(message);
    }

}
