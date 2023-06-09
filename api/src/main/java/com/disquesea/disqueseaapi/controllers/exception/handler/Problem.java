package com.disquesea.disqueseaapi.controllers.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;

    private String type;

    private String title;

    private String detail;

    private String userMessage;

    private LocalDateTime timestamp;

    private List<Field> violations;


    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }

}
