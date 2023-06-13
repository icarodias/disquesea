package com.disquesea.disqueseaapi.controllers;

import com.disquesea.disqueseaapi.components.DateCustom;
import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.services.DocumentService;
import com.disquesea.disqueseaapi.domain.services.OrderService;
import com.disquesea.disqueseaapi.specifications.dto.OrderCriteriaDTO;
import com.disquesea.disqueseaapi.controllers.dtos.requests.CreateOrderDTO;
import com.disquesea.disqueseaapi.controllers.dtos.responses.OrderResponseDTO;
import com.disquesea.disqueseaapi.controllers.mappers.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    private final DocumentService documentService;

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

    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> generateOrderHistory() {
        final String date = LocalDate.now().format(DateCustom.DATE_FILE_NAME_FORMAT);
        final String headerValues = String.format("attachment; filename=\"order-history-%s.pdf\"",date);

        return ResponseEntity.ok()
                .contentType((MediaType.APPLICATION_OCTET_STREAM))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(documentService.generateOrderHistory());
    }

}
