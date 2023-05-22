package com.disquesea.disqueseaapi.domain.services;

import com.disquesea.disqueseaapi.domain.model.Product;
import com.disquesea.disqueseaapi.domain.respositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private Product full(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));
    }

    public Product create(Product product) {
        product.updateStatus();
        return save(product);
    }

    private Product save(Product product) {
        return repository.save(product);
    }

    public Product update(long id, Product source) {
        Product target = full(id);

        String[] ignoredProperties = {"id", "status"};
        BeanUtils.copyProperties(source, target, ignoredProperties);

        return save(target);
    }

    public void delete(long id) {
        final Product product = full(id);

        repository.delete(product);
    }
}
