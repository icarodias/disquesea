package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.model.Wallet;
import com.disquesea.disqueseaapi.domain.respositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;


    public Wallet obtain() {
        return repository.findById(1L).orElseThrow(() -> new RuntimeException("Wallet not found."));
    }

    public void reset() {
        Wallet wallet = obtain();
        wallet.reset();

        repository.save(wallet);
    }

    public void incoming(BigDecimal value) {
        Wallet wallet = obtain();
        wallet.update(value, true);

        repository.save(wallet);
    }

    public void expense(BigDecimal value) {
        Wallet wallet = obtain();
        wallet.update(value, false);

        repository.save(wallet);
    }

}
