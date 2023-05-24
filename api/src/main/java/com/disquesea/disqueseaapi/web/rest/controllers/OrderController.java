package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.services.OrderService;
import com.disquesea.disqueseaapi.specifications.dto.OrderCriteriaDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.requests.CreateOrderDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.OrderResponseDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.mappers.OrderMapper;
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

    private final OrderMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponseDTO> findAll(@ParameterObject Pageable pageable,
                                          @ParameterObject @Valid OrderCriteriaDTO criteriaDTO) {
        return service.findAll(criteriaDTO, pageable).map(mapper::map);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO create(@RequestBody @Valid CreateOrderDTO orderDTO) {
        final Order order = mapper.toDomain(orderDTO);
        return mapper.map(service.create(order));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear() {
        service.deleteAll();
    }

}
