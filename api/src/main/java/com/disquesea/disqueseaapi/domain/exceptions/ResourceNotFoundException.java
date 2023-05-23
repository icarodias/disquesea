package com.disquesea.disqueseaapi.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){
        super("Entity not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }


}
