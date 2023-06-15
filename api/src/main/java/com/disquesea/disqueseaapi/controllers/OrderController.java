package com.disquesea.disqueseaapi.controllers;

import com.disquesea.disqueseaapi.components.DateCustom;
import com.disquesea.disqueseaapi.controllers.annotation.OperationAuth;
import com.disquesea.disqueseaapi.controllers.dtos.requests.CreateOrderDTO;
import com.disquesea.disqueseaapi.controllers.dtos.responses.OrderResponseDTO;
import com.disquesea.disqueseaapi.controllers.mappers.OrderMapper;
import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.services.DocumentService;
import com.disquesea.disqueseaapi.domain.services.OrderService;
import com.disquesea.disqueseaapi.specifications.dto.OrderCriteriaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    private final DocumentService documentService;

    private final OrderMapper mapper;

    @GetMapping
    @OperationAuth(summary = "Find all Orders by Parameters")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderResponseDTO> findAll(Pageable pageable, @Valid OrderCriteriaDTO criteriaDTO) {
        return service.findAll(criteriaDTO, pageable).map(mapper::map);
    }

    @PostMapping
    @OperationAuth(summary = "Create Order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO create(@RequestBody @Valid CreateOrderDTO orderDTO) {
        final Order order = mapper.toDomain(orderDTO);
        return mapper.map(service.create(order));
    }

    @DeleteMapping
    @OperationAuth(summary = "Delete Order")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear() {
        service.deleteAll();
    }

    @GetMapping("/download")
    @OperationAuth(summary = "Download Order History")
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
