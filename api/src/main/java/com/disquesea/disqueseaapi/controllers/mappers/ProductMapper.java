package com.disquesea.disqueseaapi.controllers.mappers;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.controllers.dtos.responses.ProductResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductResponseDTO> {

    public ProductMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

}
