package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.services.WalletService;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.WalletResponseDTO;
import com.disquesea.disqueseaapi.web.rest.controllers.mappers.WalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    private final WalletMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public WalletResponseDTO obtain() {
        return mapper.map(service.obtain());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reset() {
        service.reset();
    }

}
