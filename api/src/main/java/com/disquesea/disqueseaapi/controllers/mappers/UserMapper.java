package com.disquesea.disqueseaapi.controllers.mappers;

import com.disquesea.disqueseaapi.controllers.dtos.responses.UserResponseDTO;
import com.disquesea.disqueseaapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserResponseDTO> {

    public UserMapper(ModelMapper modelMapper){
        super(modelMapper);
    }

}
