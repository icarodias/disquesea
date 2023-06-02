package com.disquesea.disqueseaapi.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.disquesea.disqueseaapi.security.dtos.UserCredentialsDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {
    
    private final UserAuthenticationService service;


    @PostMapping("/auth/login")
    public ResponseEntity<Object> auth(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        try {
            AuthToken authToken = service.authenticateUser(userCredentialsDTO);

            return ResponseEntity.accepted().header("Authorization", authToken.getToken()).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(403).build();
        }
    }
}
