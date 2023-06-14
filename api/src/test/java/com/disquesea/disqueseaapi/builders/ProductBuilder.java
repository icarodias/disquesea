package com.disquesea.disqueseaapi.builders;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.model.enums.Scale;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductBuilder {

    private Product product;

    private ProductBuilder(){}

    public static ProductBuilder oneProduct(){
        ProductBuilder builder = new ProductBuilder();
        builder.product = new Product();
        builder.product.setAmount(BigDecimal.ZERO);

        return builder;
    }

    public ProductBuilder inKg() {
        this.product.setAmount(BigDecimal.ZERO);
        this.product.updateStatus();
        this.product.setScale(Scale.KILOGRAM);

        return this;
    }

    public ProductBuilder inUnit() {
        this.product.setAmount(BigDecimal.ZERO);
        this.product.updateStatus();
        this.product.setScale(Scale.UNIT);

        return this;
    }

    public ProductBuilder withMinimalAmount() {
        Scale scale = this.product.getScale();

        final BigDecimal amount = (scale.equals(Scale.UNIT))
                ? BigDecimal.ONE.setScale(0, RoundingMode.DOWN)
                : BigDecimal.valueOf(0.001).setScale(3, RoundingMode.HALF_UP);

        product.setAmount(amount);

        return this;
    }

    public ProductBuilder withAmount(BigDecimal amount) {
        final Scale scale = this.product.getScale();
        final BigDecimal result =(scale.equals(Scale.UNIT))
                ? amount.setScale(0, RoundingMode.DOWN)
                : amount.setScale(3, RoundingMode.HALF_UP);

        product.setAmount(result);

        return this;
    }

    public Product now(){
        return product;
    }


}
