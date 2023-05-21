package com.disquesea.disqueseaapi.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @Setter(AccessLevel.NONE)
    protected BigDecimal amount = BigDecimal.ZERO;

    private BigDecimal price;

    private Boolean isVisibleInCatalog;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Scale scale;

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Status status;

    public BigDecimal getAmount() {
        return setAmountNumericalScale(amount);
    }

    public void addAmount(BigDecimal amount) {
        changeAmount(amount, true);
        updateStatus();
    }

    public void removeAmount(BigDecimal amount) {
        changeAmount(amount, false);
        updateStatus();
    }

    private void changeAmount(BigDecimal value, boolean isIncrement) {
        BigDecimal finalAmount = (isIncrement) ? this.amount.add(value) : this.amount.subtract(value);

        finalAmount = setAmountNumericalScale(finalAmount);

        this.amount = finalAmount;
    }

    private BigDecimal setAmountNumericalScale(BigDecimal value) {
         final boolean isKilogramScale = scale.equals(Scale.KILOGRAM);

         return (isKilogramScale) ? value.setScale(3, RoundingMode.HALF_UP)
                 : value.setScale(0, RoundingMode.HALF_DOWN);
    }

    private void updateStatus() {
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
