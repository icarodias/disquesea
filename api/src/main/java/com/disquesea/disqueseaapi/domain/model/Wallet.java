package com.disquesea.disqueseaapi.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "tb_wallet")
@RequiredArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;


    public void reset() {
        this.value = BigDecimal.ZERO;
    }
}
