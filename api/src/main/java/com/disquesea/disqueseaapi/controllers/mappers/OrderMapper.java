package com.disquesea.disqueseaapi.controllers.mappers;

import com.disquesea.disqueseaapi.controllers.dtos.responses.OrderResponseDTO;
import com.disquesea.disqueseaapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderResponseDTO> {

    public OrderMapper(ModelMapper modelMapper){
        super(modelMapper);
    }
}
