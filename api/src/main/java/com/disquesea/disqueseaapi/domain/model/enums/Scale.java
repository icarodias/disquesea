package com.disquesea.disqueseaapi.domain.model.enums;

public enum Scale {
    KILOGRAM("Kg"), UNIT("uni.");

    private final String description;

    Scale(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
