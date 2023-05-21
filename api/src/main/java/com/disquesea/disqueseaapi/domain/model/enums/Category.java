package com.disquesea.disqueseaapi.domain.model.enums;

public enum Category {
    FISH("Peixe"),
    SHRIMP("Camarão"),
    SEAFOOD("Frutos do Mar"),
    MEAT("Carnes"),
    BIRD("Aves"),
    SNACK("Petiscos"),
    OTHER("Diversos");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
