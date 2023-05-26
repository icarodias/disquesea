package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.domain.services.UserService;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests.CreateUserDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.UserResponseDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    private final UserMapper mapper;


    @PostMapping
    public UserResponseDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
        final User user = mapper.toDomain(createUserDTO);

        return mapper.map(service.create(user));
    }

}
