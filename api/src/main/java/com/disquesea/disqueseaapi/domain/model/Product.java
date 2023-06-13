package com.disquesea.disqueseaapi.domain.model;

import java.math.BigDecimal;

import com.disquesea.disqueseaapi.components.RoundCustomComponent;
import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import com.disquesea.disqueseaapi.domain.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_products")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount = BigDecimal.ZERO;

    private BigDecimal price;

    private Boolean isVisibleInCatalog = false;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_scale")
    private Scale scale;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Status status;

    public BigDecimal getAmount() {
        return RoundCustomComponent.roundingAmount(amount, scale);
    }

    public BigDecimal getPrice() {
        return RoundCustomComponent.roundPrice(amount);
    }

    public void changeAmount(BigDecimal value, boolean isReduction) {
        BigDecimal finalAmount = (isReduction) ? this.amount.subtract(value) : this.amount.add(value);

        finalAmount = RoundCustomComponent.roundingAmount(finalAmount, scale);

        this.amount = finalAmount;
        updateStatus();
    }

    public void updateStatus() {
        this.status = Status.getCorrectStatus(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
