package com.disquesea.disqueseaapi.controllers;

import com.disquesea.disqueseaapi.controllers.annotation.OperationAuth;
import com.disquesea.disqueseaapi.domain.services.WalletService;
import com.disquesea.disqueseaapi.controllers.dtos.responses.WalletResponseDTO;
import com.disquesea.disqueseaapi.controllers.mappers.WalletMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet API")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    private final WalletMapper mapper;

    @GetMapping
    @OperationAuth(summary = "Get Wallet content")
    @ResponseStatus(HttpStatus.OK)
    public WalletResponseDTO obtain() {
        return mapper.map(service.obtain());
    }

    @PutMapping("/reset")
    @OperationAuth(summary = "Reset Wallet content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reset() {
        service.reset();
    }

}
