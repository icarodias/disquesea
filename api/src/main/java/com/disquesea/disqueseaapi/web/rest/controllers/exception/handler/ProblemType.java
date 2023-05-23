package com.disquesea.disqueseaapi.web.rest.controllers.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {
    RESOURCE_NOT_FOUND("Resource not found", "/resource-not-found"),
    ERROR_BUSINESS("Business rule violation", "/error-business"),
    ENTITY_IS_BEING_USED("Entity is being used", "/entity-is-being-used"),
    INVALID_PARAMETER("Invalid parameter", "/invalid-parameter"),
    SYSTEM_ERROR("System error", "/system-error"),
    MESSAGE_NOT_READABLE("Message not readable", "/message-not-readable"),
    INVALID_DATA("Invalid data", "/invalid-data");

    private String uri;

    private final String title;

    ProblemType(String title, String path){
        this.uri = "http://localhost:8080" + path;
        this.title = title;
    }
}
