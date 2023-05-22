package com.disquesea.disqueseaapi.web.rest.controllers;

import com.disquesea.disqueseaapi.domain.model.Wallet;
import com.disquesea.disqueseaapi.domain.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Wallet obtain() {
        return service.obtain();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reset() {
        service.reset();
    }

}
