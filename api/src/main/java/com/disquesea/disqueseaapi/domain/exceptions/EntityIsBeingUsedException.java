package com.disquesea.disqueseaapi.domain.exceptions;

public class EntityIsBeingUsedException extends RuntimeException {

    public EntityIsBeingUsedException(){
        super("Entity is being used");
    }

    public EntityIsBeingUsedException(String message) {
        super(message);
    }
}
