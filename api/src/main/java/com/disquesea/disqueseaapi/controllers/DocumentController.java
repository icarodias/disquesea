package com.disquesea.disqueseaapi.controllers;

import com.disquesea.disqueseaapi.components.DateCustom;
import com.disquesea.disqueseaapi.domain.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService service;

    @GetMapping("/storage")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> generateStorage() {
        final String date = LocalDate.now().format(DateCustom.DATE_FILE_NAME_FORMAT);
        final String headerValues = String.format("attachment; filename=\"storage-%s.pdf\"",date);

        return ResponseEntity.ok()
                .contentType((MediaType.APPLICATION_OCTET_STREAM))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(service.generateStorage());
    }

    @GetMapping("/order-history")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> generateOrderHistory() {
        final String date = LocalDate.now().format(DateCustom.DATE_FILE_NAME_FORMAT);
        final String headerValues = String.format("attachment; filename=\"order-history-%s.pdf\"",date);

        return ResponseEntity.ok()
                .contentType((MediaType.APPLICATION_OCTET_STREAM))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(service.generateOrderHistory());
    }

}
