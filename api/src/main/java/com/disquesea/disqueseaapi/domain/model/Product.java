package com.disquesea.disqueseaapi.domain.model;

import com.disquesea.disqueseaapi.domain.model.enums.Category;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;
import com.disquesea.disqueseaapi.domain.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
        return setAmountNumericalScale(amount);
    }

    public void changeAmount(BigDecimal value, boolean isReduction) {
        BigDecimal finalAmount = (isReduction) ? this.amount.subtract(value) : this.amount.add(value);

        finalAmount = setAmountNumericalScale(finalAmount);

        this.amount = finalAmount;
        updateStatus();
    }

    private BigDecimal setAmountNumericalScale(BigDecimal value) {
         final boolean isKilogramScale = scale.equals(Scale.KILOGRAM);

         return (isKilogramScale) ? value.setScale(3, RoundingMode.HALF_UP)
                 : value.setScale(0, RoundingMode.HALF_DOWN);
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
