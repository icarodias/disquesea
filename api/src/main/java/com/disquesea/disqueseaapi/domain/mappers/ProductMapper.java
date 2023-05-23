package com.disquesea.disqueseaapi.domain.mappers;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.ProductResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductResponseDTO> {

    public ProductMapper(ModelMapper mapper, Class<Product> domainType, Class<ProductResponseDTO> transferObjectType) {
        super(mapper, domainType, transferObjectType);
    }

}
