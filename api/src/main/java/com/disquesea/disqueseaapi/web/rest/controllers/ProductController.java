package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.services.ProductService;
import com.disquesea.disqueseaapi.specifications.dto.ProductCriteriaDTO;
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

    @GetMapping(params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> findAll(@ParameterObject Pageable pageable, @ParameterObject ProductCriteriaDTO criteriaDTO) {
        return service.findAll(criteriaDTO, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Product update(@PathVariable long id,@RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
