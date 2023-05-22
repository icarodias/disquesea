package com.disquesea.disqueseaapi.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public void update(BigDecimal value, boolean isIncoming) {
        BigDecimal total = (isIncoming) ? this.value.add(value) : this.value.subtract(value);

        this.value = total.setScale(2, RoundingMode.HALF_UP);
    }

}
