package com.disquesea.disqueseaapi.domain.model.enums;

public enum Status {
    AVAILABLE("disponível"), CRITICAL("crítico"), ABSENT("indisponível");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
