package com.disquesea.disqueseaapi.web.rest.controllers.mappers;

import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.OrderResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderResponseDTO> {

    public OrderMapper(ModelMapper modelMapper, Class<Order> domainType, Class<OrderResponseDTO> transferObjectType){
        super(modelMapper, domainType, transferObjectType);
    }
}
