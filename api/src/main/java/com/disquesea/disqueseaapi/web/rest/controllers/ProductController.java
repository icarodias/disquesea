package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.web.rest.controllers.mappers.ProductMapper;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.services.ProductService;
import com.disquesea.disqueseaapi.specifications.dto.ProductCriteriaDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests.CreateProductDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests.UpdateProductDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.ProductResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    private final ProductMapper mapper;

    @GetMapping(params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponseDTO> findAll(@ParameterObject Pageable pageable,
                                            @ParameterObject @Valid ProductCriteriaDTO criteriaDTO) {
        return service.findAll(criteriaDTO, pageable).map(mapper::map);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@RequestBody @Valid CreateProductDTO productDTO) {
        Product product = mapper.toDomain(productDTO);
        return mapper.map(service.create(product));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponseDTO update(@PathVariable long id,@RequestBody @Valid UpdateProductDTO productDTO) {
        Product product = mapper.toDomain(productDTO);
        return mapper.map(service.update(id, product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
