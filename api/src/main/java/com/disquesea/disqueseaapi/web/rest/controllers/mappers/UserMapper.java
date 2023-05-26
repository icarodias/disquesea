package com.disquesea.disqueseaapi.web.rest.controllers.mappers;

import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserResponseDTO> {

    public UserMapper(ModelMapper modelMapper){
        super(modelMapper);
    }

}
