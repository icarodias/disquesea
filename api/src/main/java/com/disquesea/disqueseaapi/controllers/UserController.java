package com.disquesea.disqueseaapi.controllers;

import com.disquesea.disqueseaapi.controllers.annotation.OperationAuth;
import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.domain.services.UserService;
import com.disquesea.disqueseaapi.controllers.dtos.requests.CreateUserDTO;
import com.disquesea.disqueseaapi.controllers.dtos.responses.UserResponseDTO;
import com.disquesea.disqueseaapi.controllers.mappers.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User API")
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @PostMapping
    @OperationAuth(summary = "Create User")
    public UserResponseDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
        final User user = mapper.toDomain(createUserDTO);

        return mapper.map(service.create(user));
    }

}
