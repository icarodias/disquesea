package com.disquesea.disqueseaapi.model;

import com.disquesea.disqueseaapi.builders.ProductBuilder;
import com.disquesea.disqueseaapi.domain.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {


    @Test
    public void addAmountInKgTest() {
        //scenario
        Product productKgMinimalAmount = ProductBuilder.oneProduct().inKg().withMinimalAmount().now();

        //action
        productKgMinimalAmount.changeAmount(BigDecimal.TEN, false);

        //validation
        Assertions.assertEquals(productKgMinimalAmount.getAmount(), BigDecimal.valueOf(10.001));
    }

    @Test
    public void reduceAmountInKgTest() {
        //scenario
        final BigDecimal amount = BigDecimal.valueOf(19.005);
        Product productKgMinimalAmount = ProductBuilder.oneProduct().inKg().withAmount(amount).now();

        //action
        productKgMinimalAmount.changeAmount(BigDecimal.TEN, true);

        //validation
        Assertions.assertEquals(productKgMinimalAmount.getAmount(), BigDecimal.valueOf(9.005));
    }

    @Test
    public void addAmountInUnitTest() {
        //scenario
        Product productUnitMinimalAmount = ProductBuilder.oneProduct().inUnit().withMinimalAmount().now();

        //action
        productUnitMinimalAmount.changeAmount(BigDecimal.ONE, false);

        //validation
        Assertions.assertEquals(productUnitMinimalAmount.getAmount(), BigDecimal.valueOf(2));
    }

    @Test
    public void reduceAmountInUnitTest() {
        //scenario
        Product productUnitMinimalAmount = ProductBuilder.oneProduct().inUnit().withMinimalAmount().now();

        //action
        productUnitMinimalAmount.changeAmount(BigDecimal.TEN, false);
        productUnitMinimalAmount.changeAmount(BigDecimal.ONE, true);

        //validation
        Assertions.assertEquals(productUnitMinimalAmount.getAmount(), BigDecimal.valueOf(10));
    }


}
