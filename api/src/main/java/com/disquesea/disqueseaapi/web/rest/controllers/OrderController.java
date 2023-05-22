package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.services.OrderService;
import com.disquesea.disqueseaapi.specifications.dto.OrderCriteriaDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Order> findAll(@ParameterObject Pageable pageable, @ParameterObject @Valid OrderCriteriaDTO criteriaDTO) {
        return service.findAll(criteriaDTO, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @DeleteMapping
    public void clear() {
        service.deleteAll();
    }

}


