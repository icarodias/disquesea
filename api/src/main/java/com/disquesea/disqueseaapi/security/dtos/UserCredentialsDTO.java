package com.disquesea.disqueseaapi.security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCredentialsDTO {
    
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
