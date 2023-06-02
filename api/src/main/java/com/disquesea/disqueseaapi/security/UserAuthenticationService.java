package com.disquesea.disqueseaapi.security;

import org.springframework.stereotype.Service;

import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.domain.services.UserService;
import com.disquesea.disqueseaapi.security.components.EncoderUtil;
import com.disquesea.disqueseaapi.security.components.TokenJwtUtil;
import com.disquesea.disqueseaapi.security.dtos.UserCredentialsDTO;

@Service
public class UserAuthenticationService {

    private final UserService userService;
    private final EncoderUtil encoderUtil;
    private final TokenJwtUtil tokenJwtUtil;


    public UserAuthenticationService(UserService userService, EncoderUtil encoderUtil, TokenJwtUtil tokenJwtUtil) {
        this.userService = userService;
        this.encoderUtil = encoderUtil;
        this.tokenJwtUtil = tokenJwtUtil;
    }
    
    public AuthToken authenticateUser(UserCredentialsDTO userCredentials) {
        final User user = userService.full(userCredentials.getUsername());
        final boolean isAuthenticated = encoderUtil.passwordMatches(userCredentials.getPassword(), user.getPassword());

        if (!isAuthenticated) {
             throw new RuntimeException("Bad Credentials");
        }
       
       return tokenJwtUtil.encodeToken(user);
        
    }
}
