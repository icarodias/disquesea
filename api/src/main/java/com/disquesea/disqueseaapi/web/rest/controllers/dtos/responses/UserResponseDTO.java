package com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses;

import com.disquesea.disqueseaapi.domain.model.enums.Role;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String username;

    private String name;

    private Role role;
}
