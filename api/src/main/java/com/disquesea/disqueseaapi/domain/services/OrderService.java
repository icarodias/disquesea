package com.disquesea.disqueseaapi.domain.services;


import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.respositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final ProductService productService;

    private final WalletService walletService;

    public Page<Order> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Order create(Order order) {
        final boolean isSell = order.getIsSell();

        if( !isSell && isNull(order.getPrice())){
            throw new RuntimeException("When order is a buy, the price is obligated");
        }

        final Long productId = order.getProductId();
        Product product = productService.findById(productId);

        order.setCreatedAt(LocalDate.now());
        calculatePrice(order, product);

        setProductAfterUpdate(order, product, isSell);

        updateWallet(order, isSell);

        return repository.save(order);
    }

    private void calculatePrice(Order order, Product product) {
        if (isNull(order.getPrice())) {
            final BigDecimal price = order.getAmount().multiply(product.getPrice())
                    .setScale(2, RoundingMode.HALF_UP);

            order.setPrice(price);
        }
    }

    private void setProductAfterUpdate(Order order, Product product, boolean isSell) {
        product.changeAmount(order.getAmount(), isSell);
        productService.save(product);
        order.setProduct(product);
    }

    private void updateWallet(Order order, boolean isSell) {
        final BigDecimal value = order.getPrice();

        if (isSell) {
            walletService.incoming(value);
        } else {
            walletService.expense(value);
        }
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
