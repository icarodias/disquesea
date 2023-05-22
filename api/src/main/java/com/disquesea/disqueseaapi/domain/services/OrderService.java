package com.disquesea.disqueseaapi.domain.services;


import com.disquesea.disqueseaapi.domain.exceptions.BusinessException;
import com.disquesea.disqueseaapi.domain.exceptions.ResourceNotFoundException;
import com.disquesea.disqueseaapi.domain.model.Order;
import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.respositories.OrderRepository;
import com.disquesea.disqueseaapi.specifications.OrderSpecification;
import com.disquesea.disqueseaapi.specifications.ProductSpecification;
import com.disquesea.disqueseaapi.specifications.dto.OrderCriteriaDTO;
import com.disquesea.disqueseaapi.utils.DateCustomUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Order> findAll(OrderCriteriaDTO criteriaDTO, Pageable pageable) {
        final LocalDate fromDate = DateCustomUtils.fromString(criteriaDTO.getFromDate());

        final LocalDate toDate = DateCustomUtils.fromString(criteriaDTO.getToDate());

        Specification<Order> specification = Specification
                .where(OrderSpecification.sellIs(criteriaDTO.getIsSell()))
                .and(OrderSpecification.productIdIs(criteriaDTO.getProductId()))
                .and(OrderSpecification.productCategoryIs(criteriaDTO.getProductCategory()))
                .and(OrderSpecification.createdAfter(fromDate))
                .and(OrderSpecification.createdBefore(toDate));

        return repository.findAll(specification, pageable);
    }

    @Transactional
    public Order create(Order order) {
        final boolean isSell = order.getIsSell();

        checkOrderIntegrity(order);

        Product product = getOrderProduct(order);

        order.setCreatedAt(LocalDate.now());
        calculatePrice(order, product);

        setProductAfterUpdate(order, product, isSell);

        updateWallet(order, isSell);

        return repository.save(order);
    }

    private void checkOrderIntegrity(Order order) {
        if( !order.getIsSell() && isNull(order.getPrice())){
            throw new BusinessException("When order is a buy, the price is obligated");
        }
    }

    private Product getOrderProduct(Order order) {
        try{
            return productService.findById(order.getProductId());
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(ex.getMessage());
        }
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
